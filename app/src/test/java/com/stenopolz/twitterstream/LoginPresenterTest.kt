package com.stenopolz.twitterstream

import com.stenopolz.twitterstream.presenter.DefaultLoginPresenter
import com.stenopolz.twitterstream.presenter.LoginPresenter
import com.stenopolz.twitterstream.view.LoginView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Stenopolz on 08.08.2016.
 * Test for LoginPresenter implementation
 */
class LoginPresenterTest {

    @Mock
    lateinit var view: LoginView
    lateinit var presenter: LoginPresenter

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        presenter = DefaultLoginPresenter(view)
    }

    @Test
    fun testLoginSuccess() {
        presenter.handleLoginSuccess()
        Mockito.verify(view).showMainScreen()
    }

    @Test
    fun testLoginFailed() {
        val exception = Mockito.mock(Exception::class.java)
        Mockito.`when`(exception.message).thenReturn("")
        presenter.handleLoginFailed(exception)
        Mockito.verify(view).showError(Mockito.anyString())
    }

}
