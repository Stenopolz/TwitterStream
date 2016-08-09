package com.stenopolz.twitterstream.view

import com.stenopolz.twitterstream.model.models.Tweet

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines interface for main screen view
 */
interface MainView {
    fun setStartEnabled(enabled: Boolean)
    fun setStopEnabled(enabled: Boolean)
    fun showTweet(tweet: Tweet)
    fun showError(message: String)
    fun showLoginScreen()
}
