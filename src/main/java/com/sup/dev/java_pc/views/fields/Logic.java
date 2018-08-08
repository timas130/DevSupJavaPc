package com.sup.dev.java_pc.views.fields;


import com.sup.dev.java.classes.callbacks.list.CallbacksList1;
import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.classes.callbacks.simple.Callback1;
import com.sup.dev.java.classes.providers.Provider1;
import com.sup.dev.java.tools.ToolsTextJava;
import com.sup.dev.java_pc.views.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Logic implements MouseListener {

    public static final Color COLOR_ERROR = GUI.RED_500;

    private final CallbacksList1<String> onChanged = new CallbacksList1<>();
    private final JTextComponent textComponent;

    private Provider1<String, Boolean> filter;
    private Provider1<String, Boolean> errorChecker;
    private Callback localOnTextChanged;
    private String hint = "";
    private int w;
    private Color defBackground;

    private boolean onlyNum;
    private boolean onlyNumDouble;
    private boolean isError;

    public Logic(JTextComponent textComponent, int w, String hint) {
        this.textComponent = textComponent;
        this.w = w;

        textComponent.setFont(GUI.BODY_2);
        setHint(hint);
        setLines(1);
        setBackground(GUI.WHITE);

        textComponent.addMouseListener(this);

        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }
        });
        ((AbstractDocument) textComponent.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

                text = text.replaceAll("\n","").replaceAll("\r","");
                String s = text;

                if (offset == fb.getDocument().getLength())
                    s = fb.getDocument().getText(0, offset) + s;
                else
                    s = fb.getDocument().getText(0, offset) + s + fb.getDocument().getText(offset, fb.getDocument().getLength() - offset);
                if ((filter == null || filter.provide(s)) && (!onlyNum || ToolsTextJava.isInteger(s)) && (!onlyNumDouble || ToolsTextJava.isDouble(s)))
                    super.replace(fb, offset, length, text, attrs);
            }
        });

        textComponent.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    }

    void onTextChanged() {
        if (onChanged != null) onChanged.callback(getText());
        if (localOnTextChanged != null) localOnTextChanged.callback();
        if (errorChecker != null) setErrorState(errorChecker.provide(getText()));
    }

    public void paint(Graphics g) {

        if (getText().length() == 0 && hint != null) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            FontMetrics fm = g.getFontMetrics();
            int c0 = textComponent.getBackground().getRGB();
            int c1 = textComponent.getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.setFont(GUI.CAPTION);
            g.drawString(hint, 4, fm.getHeight() + fm.getAscent() / 2 - (textComponent instanceof ZField ? 2 : 6));
        }


    }

    public void showIfError() {
        onTextChanged();
    }

    //
    //  Setters
    //


    public void setLocalOnTextChanged(Callback localOnTextChanged) {
        this.localOnTextChanged = localOnTextChanged;
    }

    public void setErrorIfEmpty() {
        setOnChangedErrorChecker(String::isEmpty);
    }

    public void setOnChangedErrorChecker(Provider1<String, Boolean> onChanged) {
        addOnChanged(source -> setErrorState(onChanged.provide(source)));
    }

    public void addOnChanged(Callback1<String> onChanged) {
        this.onChanged.add(onChanged);
    }

    public void setFilter(Provider1<String, Boolean> filter) {
        this.filter = filter;
    }

    public void setHint(String hint) {
        this.hint = hint == null ? "" : hint;
        textComponent.invalidate();
    }

    public void setBackground(Color color) {
        defBackground = color;
        if(isError)return;
        ((Field) textComponent).setBackgroundSuper(color);
    }

    private void setBackgroundNoDef(Color color) {
        ((Field) textComponent).setBackgroundSuper(color);
    }

    private void setErrorState(boolean b) {
        isError = b;
        setBackgroundNoDef(b ? COLOR_ERROR : defBackground);
    }

    public void setLines(int lines) {
        textComponent.setPreferredSize(new Dimension(w, (textComponent.getFontMetrics(textComponent.getFont()).getHeight() + 4) * lines + 12));
    }

    public void setErrorChecker(Provider1<String, Boolean> errorChecker) {
        this.errorChecker = errorChecker;
    }

    //
    //  Getters
    //

    private String getText() {
        return textComponent.getText();
    }

    public int getInt() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException n) {
            return 0;
        }

    }

    public double getDouble() {
        try {
            return Double.parseDouble(getText().replace(',', '.'));
        } catch (NumberFormatException n) {
            return 0;
        }
    }

    public String getHint() {
        return hint;
    }

    public void setOnlyNum() {
        this.onlyNum = true;
    }

    public void setOnlyNumDouble() {
        this.onlyNumDouble = true;
    }

    public boolean isError() {
        return isError;
    }

    //
    //  Mouse
    //

    private Callback onRightClick;

    public void setOnRightClick(Callback onRightClick) {
        this.onRightClick = onRightClick;
    }

    private boolean pressed  = false;


    @Override
    public void mouseClicked(MouseEvent e) {
        pressed  = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed  = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pressed && e.getButton() == 3 && onRightClick != null) onRightClick.callback();
        pressed  = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pressed  = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        pressed  = false;
    }


}
