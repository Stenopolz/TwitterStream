package com.stenopolz.twitterstream.presenter

import com.stenopolz.twitterstream.view.LoginView

/**
 * Created by Stenopolz on 07.08.2016.
 */
class DefaultLoginPresenter(view: LoginView) : LoginPresenter {
    override fun handleLoginSuccess() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleLoginFailed(exception: Exception) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
