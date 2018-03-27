package com.sup.dev.java_pc.google;


import com.sun.mail.smtp.SMTPTransport;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GoogleMail {

    private final String username;
    private final String password;
    private final Properties props;

    public GoogleMail(String username, String password) {
        this.username = username;
        this.password = password;

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");

    }

    public void send(String recipientEmail, String title, String message) throws MailException {
        Session session = Session.getInstance(props, null);

        final MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(username + "@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

            msg.setSubject(title);
            msg.setText(message, "utf-8");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

            t.connect("smtp.gmail.com", username, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    public static final class MailException extends Exception {

        public MailException(Exception e){
            super(e);
        }

    }

    /*

     public static void sendData(String message, String tokenId) {

        try {

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + serverKey);
            conn.setRequestProperty("Content-Type", "application/json");

            Json json = new Json();
            json.put("token", tokenId);
            json.put("data", message);

            Json jsonRoot = new Json();
            jsonRoot.put("message", json);

            Debug.log(jsonRoot);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonRoot.toString());
            wr.flush();

            int status = conn.getResponseCode();
            if (status != 0) Debug.log("Google notification sending error. code = " + status);

        } catch (IOException ex){
            Debug.log(ex);
        }

    }

     */

}