package com.sn.plugin_common.utils.email

import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.MimeMessage




object EmailManager {
    var  SMTP = "smtp.163.com"
    var  SEND_EMAIL = "18810798425@163.com"
    var  RECV_EMAIL ="guoxu@hualaikeji.com"
    var  RECV_EMAIL_PASSWORD ="LFDITQESCFDWJUNY"



    fun sendTextEmail(){
        val props = Properties()
        props["mail.smtp.host"] = SMTP
        val session = Session.getInstance(props, null)
        val msg = MimeMessage(session)
        msg.setFrom(SEND_EMAIL);
        msg.setRecipients(Message.RecipientType.TO, RECV_EMAIL)
        msg.subject = "JavaMail hello world example"
        msg.sentDate = Date()
        msg.setText("Hello, world!\n")
        Transport.send(msg, SEND_EMAIL, RECV_EMAIL_PASSWORD)
    }
}