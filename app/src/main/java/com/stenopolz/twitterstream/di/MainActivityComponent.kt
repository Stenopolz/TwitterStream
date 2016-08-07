package com.stenopolz.twitterstream.di

import com.stenopolz.twitterstream.view.MainActivity
import dagger.Component

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines main activity component
 */
@Component(modules = arrayOf(MainActivityModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}
