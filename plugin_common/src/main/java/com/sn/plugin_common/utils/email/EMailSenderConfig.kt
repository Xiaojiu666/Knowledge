package com.sn.plugin_common.utils.email

import java.util.*

class EMailSenderConfig {
    var mailServerHost: String? = null
    var mailServerPort = "25"
    var fromAddress: String? = null
    var toAddress: String? = null
    var userName: String? = null
    var password: String? = null
    var isValidate = false
    var eMailSenderMessage: EMailSenderMessage? = null

    val properties: Properties
        get() {
            val p = Properties()
            p["mail.smtp.host"] = mailServerHost
            p["mail.smtp.port"] = mailServerPort
            p["mail.smtp.auth"] = if (isValidate) "true" else "false"
            return p
        }

}