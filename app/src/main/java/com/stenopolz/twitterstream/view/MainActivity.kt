package com.stenopolz.twitterstream.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.stenopolz.twitterstream.App
import com.stenopolz.twitterstream.R
import com.stenopolz.twitterstream.model.TweetRepository
import com.stenopolz.twitterstream.model.models.Tweet
import com.stenopolz.twitterstream.presenter.MainPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter
    @Inject
    lateinit var repository: TweetRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).inject(this)
    }

    override fun onResume() {
        super.onResume()
        val button = findViewById(R.id.start)
        button?.setOnClickListener {
            repository.getTweets("android").subscribe({ tweet -> Log.d("Tweet: ", tweet.text) })
        }
    }


    override fun showTweet(tweet: Tweet) {

    }
}
