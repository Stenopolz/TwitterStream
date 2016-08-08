package com.stenopolz.twitterstream.model

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.stenopolz.twitterstream.model.models.Tweet
import com.stenopolz.twitterstream.model.models.TweetObject
import okhttp3.ResponseBody
import rx.Observable
import rx.functions.Func1
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Stenopolz on 07.08.2016.
 * Default implementation for TweetRepository
 */
class DefaultTweetRepository(val api: TwitterApi, val objectMapper: ObjectMapper) : TweetRepository {

    override fun getTweets(searchQuery: String): Observable<Tweet> {
        return api.getFeed(searchQuery, "en")
                .subscribeOn(Schedulers.io())
                .retryWhen({ errors ->
                    errors
                            .zipWith(Observable.range(0, Int.MAX_VALUE), { any: Any?, i: Int -> i })
                            .flatMap({ Observable.timer(1 * Math.pow(2.toDouble(), it.toDouble()).toLong(), TimeUnit.MINUTES) })
                })
                .flatMap(TransformBodyToStrings())
                .map(ParseTweetObjects(objectMapper))
                .filter({ tweet -> tweet != null })
    }

    class TransformBodyToStrings : Func1<ResponseBody, Observable<String>> {
        override fun call(body: ResponseBody): Observable<String> {
            val source = body.source()
            return Observable.create({ subscriber ->
                try {
                    while (!source.exhausted()) {
                        subscriber.onNext(source.readUtf8Line())
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    subscriber.onError(e)
                }
                subscriber.onCompleted()
            })
        }
    }

    class ParseTweetObjects(val objectMapper: ObjectMapper) : Func1<String, Tweet> {
        override fun call(string: String?): Tweet? {
            var tweet: Tweet? = null
            try {
                val tweetObject = objectMapper.readValue(string, TweetObject::class.java)
                if (tweetObject.text != null && tweetObject.createdAt != null && tweetObject.user != null &&
                        tweetObject.user.name != null && tweetObject.user.nickname != null) {
                    tweet = Tweet(tweetObject.text, tweetObject.createdAt, tweetObject.user.name, tweetObject.user.nickname)
                }
            } catch (e: JsonMappingException) {
                e.printStackTrace()
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }
            return tweet
        }

    }
}
