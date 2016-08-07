package com.stenopolz.twitterstream.model.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Stenopolz on 07.08.2016.
 * Defines model for Twitter user object
 */
public class TwitterUserObject {

    public final String name;
    public final String nickname;

    public TwitterUserObject(@JsonProperty("name") String name, @JsonProperty("screen_name") String nickname) {
        this.name = name;
        this.nickname = nickname;
    }
}
