package com.stenopolz.twitterstream.model

import com.stenopolz.twitterstream.model.models.Tweet
import rx.Observable

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines repository interface for twitter api access
 */
interface TweetRepository {
    fun getTweets(searchQuery: String): Observable<Tweet>
}
