package com.stenopolz.twitterstream.di

import com.stenopolz.twitterstream.presenter.DefaultLoginPresenter
import com.stenopolz.twitterstream.presenter.LoginPresenter
import com.stenopolz.twitterstream.view.LoginActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines login activity module
 */
@Module
class LoginActivityModule(val activity: LoginActivity) {

    @Provides
    fun provideLoginPresenter(): LoginPresenter {
        return DefaultLoginPresenter(activity)
    }
}
