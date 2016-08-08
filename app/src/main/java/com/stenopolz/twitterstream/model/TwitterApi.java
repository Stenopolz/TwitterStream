package com.stenopolz.twitterstream.model;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by Stenopolz on 07.08.2016.
 * Interface that defines Twitter Streaming Api Access
 */
public interface TwitterApi {

    @GET("/1.1/statuses/filter.json")
    @Streaming
    Observable<ResponseBody> getFeed(@Query("track") String track, @Query("language") String language);
}
