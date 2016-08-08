package com.stenopolz.twitterstream.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
    lateinit var adapter: TweetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById(R.id.list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TweetAdapter()
        recyclerView.adapter = adapter
        (application as App).inject(this)

    }

    override fun onResume() {
        super.onResume()
        presenter.startUpdates()
    }


    override fun showTweet(tweet: Tweet) {
        adapter.addTweet(tweet)
    }
}
