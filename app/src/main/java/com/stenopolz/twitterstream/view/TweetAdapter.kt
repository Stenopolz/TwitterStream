package com.stenopolz.twitterstream.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stenopolz.twitterstream.R
import com.stenopolz.twitterstream.model.models.Tweet
import java.util.*

/**
 * Created by Stenopolz on 08.08.2016.
 * Defines a RecyclerView Adapter that displays tweet in a list
 */
class TweetAdapter : RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    private val tweetList: MutableList<Tweet> = LinkedList()

    fun addTweet(tweet: Tweet) {
        //TODO: we can run out of memory here by infinitely adding objects.
        //Probably we need some buffer or db cache. Decided not to handle this for the test app.
        tweetList.add(0, tweet)
        notifyItemInserted(0)
    }

    override fun onBindViewHolder(holder: TweetAdapter.ViewHolder, position: Int) {
        val tweet = tweetList[position]
        holder.userNameTextView.text = tweet.userName
        holder.nickNameTextView.text = "@" + tweet.userNickName
        holder.messageTextView.text = tweet.text
    }

    override fun getItemCount(): Int {
        return tweetList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_tweet, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.user_name_text_view) as TextView
        val nickNameTextView: TextView = itemView.findViewById(R.id.nick_name_text_view) as TextView
        val messageTextView: TextView = itemView.findViewById(R.id.message_text_view) as TextView
    }
}
