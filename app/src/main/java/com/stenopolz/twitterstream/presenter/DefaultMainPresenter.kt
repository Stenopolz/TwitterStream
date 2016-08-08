package com.stenopolz.twitterstream.presenter

import com.stenopolz.twitterstream.model.TweetRepository
import com.stenopolz.twitterstream.model.models.Tweet
import com.stenopolz.twitterstream.view.MainView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by Stenopolz on 07.08.2016.
 * Default implementation of MainPresenter
 */
class DefaultMainPresenter(private val view: MainView, private val repository: TweetRepository) : MainPresenter {
    init {
        view.setStartEnabled(true)
        view.setStopEnabled(false)
    }

    private var subscription: Subscription? = null

    override fun startUpdates() {
        view.setStartEnabled(false)
        view.setStopEnabled(true)
        subscription = repository.getTweets("android")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNextTweet(), onError())
    }

    private fun onNextTweet(): (Tweet) -> Unit = { view.showTweet(it) }

    private fun onError(): (Throwable) -> Unit = {
        view.showError(if (it.message != null) it.message as String else "Unknown error occured!")
    }

    override fun stopUpdates() {
        view.setStartEnabled(true)
        view.setStopEnabled(false)
        subscription?.unsubscribe()
        subscription = null
    }
}
