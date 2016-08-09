package com.stenopolz.twitterstream.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import com.stenopolz.twitterstream.App
import com.stenopolz.twitterstream.R
import com.stenopolz.twitterstream.model.models.Tweet
import com.stenopolz.twitterstream.presenter.MainPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {
    @Inject
    lateinit var presenter: MainPresenter
    lateinit var adapter: TweetAdapter
    lateinit var startButton: Button
    lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_button) as Button
        startButton.setOnClickListener { presenter.startUpdates() }

        stopButton = findViewById(R.id.stop_button) as Button
        stopButton.setOnClickListener { presenter.stopUpdates() }

        val recyclerView = findViewById(R.id.list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TweetAdapter()
        recyclerView.adapter = adapter
        (application as App).inject(this)
    }

    override fun showTweet(tweet: Tweet) {
        adapter.addTweet(tweet)
    }

    override fun setStartEnabled(enabled: Boolean) {
        startButton.isEnabled = enabled
    }

    override fun setStopEnabled(enabled: Boolean) {
        stopButton.isEnabled = enabled
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
