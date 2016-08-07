package com.stenopolz.twitterstream.di

import dagger.Component

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines Application level component
 */
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
}
