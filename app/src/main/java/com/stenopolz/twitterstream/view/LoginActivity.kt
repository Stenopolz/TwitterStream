package com.stenopolz.twitterstream.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stenopolz.twitterstream.App
import com.stenopolz.twitterstream.R
import com.stenopolz.twitterstream.presenter.LoginPresenter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView {
    private var loginButton: TwitterLoginButton? = null
    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).inject(this)

        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.twitter_login_button) as TwitterLoginButton
        loginButton?.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                presenter.handleLoginSuccess()
            }

            override fun failure(exception: TwitterException) {
                presenter.handleLoginFailed(exception)
            }
        }

    }

    override fun showError(message: String) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton?.onActivityResult(requestCode, resultCode, data)
    }
}
