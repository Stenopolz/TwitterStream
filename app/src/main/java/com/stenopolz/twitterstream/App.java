package com.stenopolz.twitterstream;

import android.app.Application;
import android.support.annotation.NonNull;

import com.stenopolz.twitterstream.di.ApplicationComponent;
import com.stenopolz.twitterstream.di.DaggerApplicationComponent;
import com.stenopolz.twitterstream.di.DaggerLoginActivityComponent;
import com.stenopolz.twitterstream.di.DaggerMainActivityComponent;
import com.stenopolz.twitterstream.di.LoginActivityComponent;
import com.stenopolz.twitterstream.di.LoginActivityModule;
import com.stenopolz.twitterstream.di.MainActivityComponent;
import com.stenopolz.twitterstream.di.MainActivityModule;
import com.stenopolz.twitterstream.view.LoginActivity;
import com.stenopolz.twitterstream.view.MainActivity;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Stenopolz on 07.08.2016.
 * Application override class
 * Written in Java for Dagger support
 */
public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new TwitterCore(new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET)));
        applicationComponent = DaggerApplicationComponent.create();
    }

    public void inject(@NonNull MainActivity activity) {
        MainActivityModule activityModule = new MainActivityModule(activity);
        MainActivityComponent component = DaggerMainActivityComponent.builder()
                .mainActivityModule(activityModule)
                .applicationComponent(applicationComponent).build();
        component.inject(activity);
    }

    public void inject(@NonNull LoginActivity activity) {
        LoginActivityModule activityModule = new LoginActivityModule(activity);
        LoginActivityComponent component = DaggerLoginActivityComponent.builder()
                .loginActivityModule(activityModule)
                .applicationComponent(applicationComponent).build();
        component.inject(activity);
    }
}
