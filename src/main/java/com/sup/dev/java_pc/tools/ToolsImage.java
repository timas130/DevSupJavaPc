package com.sup.dev.java_pc.tools;


import com.sup.dev.java.libs.debug.Debug;
import com.sup.dev.java.tools.ToolsColor;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ToolsImage {


    public static void replaceColors(BufferedImage source, int color) {
        WritableRaster raster = source.getRaster();
        int[] pixel = new int[4];
        for (int xx = 0; xx < source.getWidth(); xx++) {
            for (int yy = 0; yy < source.getHeight(); yy++) {
                raster.getPixel(xx, yy, pixel);

                pixel[0] = color;

                raster.setPixel(xx, yy, pixel);
            }
        }
    }


    public static BufferedImage getBufferedImage(byte[] array) {
        try {
            return ImageIO.read(new ByteArrayInputStream(array));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isPNG(byte[] img) {
        byte[] pngH = {0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A};
        for (int i = 0; i < pngH.length; i++)
            if (img[i] != pngH[i] && img[i+1] != pngH[i])
                return false;
        return true;
    }

    public static boolean isGIF(byte[] img) {
        byte[] gifH = {0x47, 0x49, 0x46};
        for (int i = 0; i < gifH.length; i++) {
            if (img[i] != gifH[i])
                return false;
        }
        return true;
    }

    public static  int[] getImgScaleUnknownType(byte[] img) {
        return getImgScaleUnknownType(img, true, true, true);
    }

    public static  int[] getImgScaleUnknownType(byte[] img, boolean png, boolean gif, boolean jpg) {
        if (png && isPNG(img))
            return getImgScalePNG(img);
        else if (gif && isGIF(img))
            return getImgScaleGIF(img);
        else if (jpg)
            return getImgScaleJPG(img);
        throw new IllegalArgumentException("Unknown Img type");
    }

    public static int[] getImgScaleJPG(byte[] img) {
        return getImgScale("jpg", img);
    }

    public static int[] getImgScaleGIF(byte[] img) {
        return getImgScale("gif", img);
    }

    public static int[] getImgScalePNG(byte[] img) {
        return getImgScale("png", img);
    }

    private static int[] getImgScale(String suffix, byte[] img) {
        ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
        try {
            reader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(img)), false);
            return new int[]{reader.getWidth(0), reader.getHeight(0)};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static boolean checkImageScaleUnknownType(byte[] img, int w, int h) {
        return checkImageScaleUnknownType(img, w, h, true, true, true);
    }

    public static boolean checkImageScaleUnknownType(byte[] img, int w, int h, boolean png, boolean gif, boolean jpg) {
        if (png && isPNG(img))
            return checkImageScalePNG(img, w, h);
        else if (gif && isGIF(img))
            return checkImageScaleGIF(img, w, h);
        else if (jpg)
            return checkImageScaleJPG(img, w, h);
        throw new IllegalArgumentException("Unknown Img type");
    }

    public static boolean checkImageScaleJPG(byte[] img, int w, int h) {
        return checkImageScalePNG("jpg", img, w, h);
    }

    public static boolean checkImageScaleGIF(byte[] img, int w, int h) {
        return checkImageScalePNG("gif", img, w, h);
    }

    public static boolean checkImageScalePNG(byte[] img, int w, int h) {
        return checkImageScalePNG("png", img, w, h);
    }

    private static boolean checkImageScalePNG(String suffix, byte[] img, int w, int h) {
        int[] imgScale = getImgScale(suffix, img);
        return imgScale[0] == w && imgScale[1] == h;
    }

    public static boolean checkImageMaxScaleUnknownType(byte[] img, int w, int h) {
        return checkImageMaxScaleUnknownType(img, w, h, true, true, true);
    }

    public static boolean checkImageMaxScaleUnknownType(byte[] img, int w, int h, boolean png, boolean gif, boolean jpg) {
        if (png && isPNG(img))
            return checkImageMaxScalePNG(img, w, h);
        else if (gif && isGIF(img))
            return checkImageMaxScaleGIF(img, w, h);
        else if (jpg)
            return checkImageMaxScaleJPG(img, w, h);
        throw new IllegalArgumentException("Unknown Img type");
    }

    public static boolean checkImageMaxScaleJPG(byte[] img, int w, int h) {
        return checkImageMaxScalePNG("jpg", img, w, h);
    }

    public static boolean checkImageMaxScaleGIF(byte[] img, int w, int h) {
        return checkImageMaxScalePNG("gif", img, w, h);
    }

    public static boolean checkImageMaxScalePNG(byte[] img, int w, int h) {
        return checkImageMaxScalePNG("png", img, w, h);
    }

    private static boolean checkImageMaxScalePNG(String suffix, byte[] img, int w, int h) {
        int[] imgScale = getImgScale(suffix, img);
        return imgScale[0] <= w && imgScale[1] <= h;
    }




    public static boolean checkImageScaleHaARD(byte[] img, int w, int h) {
        return checkImageScale(getBufferedImage(img), w, h);
    }

    public static boolean checkImageScale(BufferedImage bufferedImage, int w, int h) {
        return bufferedImage.getWidth() == w && bufferedImage.getHeight() == h;
    }

    public static BufferedImage scaledInstance(BufferedImage source, float newW, float newH) {
        float oldW = source.getWidth();
        float oldH = source.getHeight();
        BufferedImage instance = new BufferedImage((int) newW, (int) newH, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(newW / oldW, newH / oldH);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(source, instance);
    }

    public static void filter(BufferedImage source, int color) {
        replaceColors(source, 0, 0, source.getWidth(), source.getHeight(), color, true);
    }

    public static void replaceColors(BufferedImage source, int x, int y, int w, int h, int color, boolean ignoreAlpha) {
        int a = ToolsColor.alpha(color);
        int r = ToolsColor.red(color);
        int g = ToolsColor.green(color);
        int b = ToolsColor.blue(color);
        WritableRaster raster = source.getRaster();
        int[] pixel = new int[4];
        for (int xx = x; xx < w; xx++) {
            for (int yy = y; yy < h; yy++) {
                raster.getPixel(xx, yy, pixel);
                if (ignoreAlpha) {
                    if (pixel[3] == 0) continue;
                } else {
                    pixel[3] = a;
                }
                pixel[0] = r;
                pixel[1] = g;
                pixel[2] = b;
                raster.setPixel(xx, yy, pixel);
            }
        }
    }

    public static void setAlpha(BufferedImage source, int alpha) {
        setAlpha(source, 0, 0, source.getWidth(), source.getHeight(), alpha);
    }

    public static void setAlpha(BufferedImage source, int x, int y, int w, int h, int alpha) {
        WritableRaster raster = source.getRaster();
        int[] pixel = new int[4];
        for (int xx = x; xx < w; xx++) {
            for (int yy = y; yy < h; yy++) {
                raster.getPixel(xx, yy, pixel);
                pixel[3] = alpha;
                raster.setPixel(xx, yy, pixel);
            }
        }
    }

    public static BufferedImage toCircle(BufferedImage source) {
        int diameter = Math.min(source.getWidth(), source.getHeight());
        BufferedImage mask = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();

        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        applyQualityRenderingHints(g2d);
        int x = (diameter - source.getWidth()) / 2;
        int y = (diameter - source.getHeight()) / 2;
        g2d.drawImage(source, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();
        return masked;
    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    }


}
