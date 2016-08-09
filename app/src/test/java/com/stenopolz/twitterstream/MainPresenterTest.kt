package com.stenopolz.twitterstream

import com.stenopolz.twitterstream.model.TweetRepository
import com.stenopolz.twitterstream.model.models.Tweet
import com.stenopolz.twitterstream.presenter.DefaultMainPresenter
import com.stenopolz.twitterstream.presenter.MainPresenter
import com.stenopolz.twitterstream.view.MainView
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import rx.Observable

/**
 * Created by Stenopolz on 08.08.2016.
 * Test for MainPresenter implementation
 */
class MainPresenterTest {
    @Mock
    lateinit var repository: TweetRepository
    @Mock
    lateinit var view: MainView
    lateinit var presenter: MainPresenter

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        presenter = DefaultMainPresenter(view, repository)
        Mockito.reset(view)
    }

    @Test
    fun testStartUpdates() {
        val tweet = Tweet("","","","")
        Mockito.`when`(repository.getTweets(Mockito.anyString())).thenReturn(Observable.just(tweet))
        presenter.startUpdates()
        verify(view).setStartEnabled(false)
        verify(view).setStopEnabled(true)
        verify(view).showTweet(tweet)
    }

    @Test
    fun testStopUpdates() {
        presenter.stopUpdates()
        verify(view).setStartEnabled(true)
        verify(view).setStopEnabled(false)
    }

    @Test
    fun testAuthorizationError() {
        val exception = HttpException(Response.error<Any>(401, ResponseBody.create(MediaType.parse("application/json"), "test")))
        Mockito.`when`(repository.getTweets(Mockito.anyString())).thenReturn(Observable.error(exception))
        presenter.startUpdates()
        verify(view).showError(Mockito.anyString())
        verify(view).showLoginScreen()
    }

    @Test
    fun testError() {
        val exception = Mockito.mock(Exception::class.java)
        Mockito.`when`(repository.getTweets(Mockito.anyString())).thenReturn(Observable.error(exception))
        presenter.startUpdates()
        verify(view).showError(Mockito.anyString())
    }
}
