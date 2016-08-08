package com.stenopolz.twitterstream.presenter

import com.stenopolz.twitterstream.view.LoginView

/**
 * Created by Stenopolz on 07.08.2016.
 * Default implementation of login presenter
 */
class DefaultLoginPresenter(val view: LoginView) : LoginPresenter {
    override fun handleLoginSuccess() {
        view.showMainScreen()
    }

    override fun handleLoginFailed(exception: Exception) {
        if (exception.message != null) {
            view.showError(exception.message as String)
        } else {
            view.showError("Unknown error occured")
        }
    }
}
