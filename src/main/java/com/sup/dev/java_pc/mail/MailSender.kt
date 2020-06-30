package com.sup.dev.java_pc.mail

import com.sun.mail.smtp.SMTPTransport
import com.sup.dev.java.libs.debug.err
import java.security.Security
import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailSender(
        private val username: String,
        private val password: String,
        private val smpt:String,
        private val smptPort:String ="465"

) {
    private val props: Properties

    init {

        Security.addProvider(com.sun.net.ssl.internal.ssl.Provider())
        val SSL_FACTORY = "javax.net.ssl.SSLSocketFactory"
        props = System.getProperties()
        props.setProperty("mail.smtps.host", smpt)
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY)
        props.setProperty("mail.smtp.socketFactory.fallback", "false")
        props.setProperty("mail.smtp.port", smptPort)
        props.setProperty("mail.smtp.socketFactory.port", smptPort)
        props.setProperty("mail.smtps.auth", "true")
        props["mail.smtps.quitwait"] = "false"

    }

    fun send(recipientEmail: String, title: String, message: String) {
        val session = Session.getInstance(props, null)

        val msg = MimeMessage(session)

        try {
            msg.setFrom(InternetAddress(username))
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false))

            msg.subject = title
            msg.setText(message, "utf-8")
            msg.sentDate = Date()

            val t = session.getTransport("smtps") as SMTPTransport

            t.connect(smpt, username, password)
            t.sendMessage(msg, msg.allRecipients)
            t.close()
        } catch (e: Throwable) {
            err(e)
            throw e
        }

    }


}