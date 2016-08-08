package com.stenopolz.twitterstream.presenter

import com.stenopolz.twitterstream.model.TweetRepository
import com.stenopolz.twitterstream.view.MainView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by Stenopolz on 07.08.2016.
 * Default implementation of MainPresenter
 */
class DefaultMainPresenter(private val view: MainView, private val repository: TweetRepository) : MainPresenter {
    private var subscription: Subscription? = null

    override fun startUpdates() {
        subscription?.unsubscribe()
        subscription = repository.getTweets("juno")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{view.showTweet(it)}
    }

    override fun stopUpdates() {
        subscription?.unsubscribe()
        subscription = null
    }
}
