package com.stenopolz.twitterstream.presenter

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines interface for Login Presenter
 */
interface LoginPresenter {
    fun handleLoginSuccess()
    fun handleLoginFailed(exception: Exception)
}
