package com.stenopolz.twitterstream.presenter

import com.stenopolz.twitterstream.model.TweetRepository
import com.stenopolz.twitterstream.view.MainView

/**
 * Created by Stenopolz on 07.08.2016.
 * Default implementation of MainPresenter
 */
class DefaultMainPresenter(val view: MainView, val repository: TweetRepository) : MainPresenter {
    override fun startUpdates() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopUpdates() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
