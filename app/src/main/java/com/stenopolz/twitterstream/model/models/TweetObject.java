package com.stenopolz.twitterstream.model.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines model for Tweet object
 */
public class TweetObject {

    public final String text;
    public final String createdAt;
    public final TwitterUserObject user;

    @JsonCreator
    public TweetObject(@JsonProperty("text") String text,
                       @JsonProperty("created_at") String createdAt,
                       @JsonProperty("user") TwitterUserObject user) {
        this.text = text;
        this.createdAt = createdAt;
        this.user = user;
    }
}
