package com.stenopolz.twitterstream.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.stenopolz.twitterstream.BuildConfig
import com.stenopolz.twitterstream.model.DefaultTweetRepository
import com.stenopolz.twitterstream.model.TweetRepository
import com.stenopolz.twitterstream.model.TwitterApi
import com.stenopolz.twitterstream.presenter.DefaultMainPresenter
import com.stenopolz.twitterstream.presenter.MainPresenter
import com.stenopolz.twitterstream.view.MainActivity
import com.twitter.sdk.android.core.TwitterCore
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines main activity module
 */
@Module
class MainActivityModule(val activity: MainActivity) {

    @Provides

    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Provides
    fun provideTwitterApi(objectMapper: ObjectMapper): TwitterApi {
        val consumer = OkHttpOAuthConsumer(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET)
        //by this time we have a valid token
        val token = TwitterCore.getInstance().sessionManager.activeSession.authToken
        consumer.setTokenWithSecret(token.token, token.secret)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://stream.twitter.com")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClient.Builder()
                        .addInterceptor(SigningInterceptor(consumer))
                        .build())
                .build()
        return retrofit.create(TwitterApi::class.java)
    }

    @Provides
    fun provideTweetRepository(twitterApi: TwitterApi, objectMapper: ObjectMapper): TweetRepository {
        return DefaultTweetRepository(twitterApi, objectMapper)
    }

    @Provides
    fun provideMainPresenter(tweetRepository: TweetRepository): MainPresenter {
        return DefaultMainPresenter(activity, tweetRepository)
    }
}
