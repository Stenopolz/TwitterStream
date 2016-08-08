package com.stenopolz.twitterstream.model

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.stenopolz.twitterstream.model.models.Tweet
import com.stenopolz.twitterstream.model.models.TweetObject
import okhttp3.ResponseBody
import retrofit2.adapter.rxjava.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
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
                .flatMap(TransformBodyToStrings())
                .retryWhen(handleHTTP420Error())
                .retryWhen(handleHTTPError())
                .retryWhen(handleIOException())
                .map(ParseTweetObjects(objectMapper))
                .filter({ tweet -> tweet != null })
                .observeOn(AndroidSchedulers.mainThread())
    }

    /*
        Back off linearly for TCP/IP level network errors.
        These problems are generally temporary and tend to clear quickly.
        Increase the delay in reconnects by 250ms each attempt, up to 16 seconds.
     */
    private fun handleIOException(): (Observable<out Throwable>) -> Observable<Long> {
        return { errors ->
            errors.flatMap({
                if (it is IOException) {
                    Observable.just(it)
                } else {
                    Observable.error(it as Throwable)
                }
            })
                    .zipWith(Observable.range(0, Int.MAX_VALUE), { any: Any?, i: Int -> i })
                    .flatMap({
                        var time = 250 * it
                        time = if (time <= 16000) time else 16000
                        Observable.timer(time.toLong(), TimeUnit.MILLISECONDS)
                    })
        }
    }

    /*
        Back off exponentially for HTTP errors for which reconnecting would be appropriate.
        Start with a 5 second wait, doubling each attempt, up to 320 seconds.
     */
    private fun handleHTTPError(): (Observable<out Throwable>) -> Observable<Long> {
        return { errors ->
            errors.flatMap({
                if (it is HttpException) {
                    Observable.just(it)
                } else {
                    Observable.error(it as Throwable)
                }
            })
                    .zipWith(Observable.range(0, Int.MAX_VALUE), { any: Any?, i: Int -> i })
                    .flatMap({
                        var time = 5 * Math.pow(2.toDouble(), it.toDouble()).toLong()
                        time = if (time <= 320) time else 320
                        Observable.timer(time, TimeUnit.SECONDS)
                    })
        }
    }

    /*
        Back off exponentially for HTTP 420 errors. Start with a 1 minute wait and double each attempt.
        Note that every HTTP 420 received increases the time you must wait
        until rate limiting will no longer will be in effect for your account.
     */
    private fun handleHTTP420Error(): (Observable<out Throwable>) -> Observable<Long> {
        return { errors ->
            errors.flatMap({
                if (it is HttpException && it.code() == 420) {
                    Observable.just(it)
                } else {
                    Observable.error(it as Throwable)
                }
            })
                    .zipWith(Observable.range(0, Int.MAX_VALUE), { any: Any?, i: Int -> i })
                    .flatMap({ Observable.timer(1 * Math.pow(2.toDouble(), it.toDouble()).toLong(), TimeUnit.MINUTES) })
        }
    }

    private class TransformBodyToStrings : Func1<ResponseBody, Observable<String>> {
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

    private class ParseTweetObjects(val objectMapper: ObjectMapper) : Func1<String, Tweet> {
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
