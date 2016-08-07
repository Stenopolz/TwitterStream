package com.stenopolz.twitterstream.view

import com.stenopolz.twitterstream.model.models.Tweet

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines interface for main screen view
 */
interface MainView {
    fun showTweet(tweet: Tweet)
}
