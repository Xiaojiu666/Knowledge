package com.sn.utils.email

import android.util.Log
import java.io.IOException
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

/**
 * https://blog.csdn.net/tangedegushi/article/details/81238136
 */
object EmailManager {
    var SMTP = "smtp.163.com"
    var SEND_EMAIL = "18810798425@163.com"
    var RECV_EMAIL = "guoxu@hualaikeji.com"
    var RECV_EMAIL_PASSWORD = "LFDITQESCFDWJUNY"
    val TAG = "EmailManager"

    fun sendEmail(eMailSenderConfig: EMailSenderConfig) {

        val sendMailSession = createSession(eMailSenderConfig)

        val mailMessage: Message = createMessageBody(sendMailSession, eMailSenderConfig)

        Transport.send(mailMessage)
    }


    /**
     *  把参数拼装成Transport类所需要的对象
     */
    private fun createMessageBody(
        sendMailSession: Session?,
        eMailSenderConfig: EMailSenderConfig
    ): Message {
        // 根据session创建一个邮件消息
        val mailMessage: Message = MimeMessage(sendMailSession)
        // 创建邮件发送者地址
        val from: Address = InternetAddress(eMailSenderConfig.fromAddress)
        // 创建邮件的接收者地址，并设置到邮件消息中
        val to: Address = InternetAddress(eMailSenderConfig.toAddress)

        mailMessage.setFrom(from)
        mailMessage.setRecipient(Message.RecipientType.TO, to)
        mailMessage.sentDate = Date()

        //-------------------body----------------
        val sendMessage = eMailSenderConfig.eMailSenderMessage;
        mailMessage.subject = sendMessage?.subject

        // 添加消息附件体
        val multipart = MimeMultipart()
        val messageBodyPart: BodyPart = MimeBodyPart()
        messageBodyPart.setContent(sendMessage?.content, "text/html;charset=utf-8")
        multipart.addBodyPart(messageBodyPart)
        val attachFileNames = sendMessage!!.attachFileNames
        if (attachFileNames != null && !attachFileNames.isEmpty()) {
            Log.e(TAG, "attachFileNames.size : ${attachFileNames.size}")
            for (index in attachFileNames.indices) {
                val bodyPart = MimeBodyPart()
                try {
                    Log.e(TAG, "attachFileNames.size : $index")
                    bodyPart.attachFile(attachFileNames[index])
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                multipart.addBodyPart(bodyPart);
            }
        }
        mailMessage.setContent(multipart)
        return mailMessage
    }

    /**
     * 根据Properties 和 认证 获取一个 Session
     *
     */
    private fun createSession(eMailSenderConfig: EMailSenderConfig): Session? {
        var authenticator: MailAuthenticator? = null
        if (eMailSenderConfig.isValidate) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator =
                MailAuthenticator(eMailSenderConfig.userName, eMailSenderConfig.password)
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        return Session.getDefaultInstance(eMailSenderConfig.properties, authenticator)
    }


}