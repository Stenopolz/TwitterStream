package com.stenopolz.twitterstream.di

import com.stenopolz.twitterstream.view.LoginActivity
import dagger.Component

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines login activity component
 */
@LoginActivityScope
@Component(modules = arrayOf(LoginActivityModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface LoginActivityComponent {
    fun inject(activity: LoginActivity)
}
