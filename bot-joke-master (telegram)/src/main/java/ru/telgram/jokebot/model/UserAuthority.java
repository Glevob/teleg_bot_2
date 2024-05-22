package ru.telgram.jokebot.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {

    PLACE_JOKES,
    MANAGE_JOKES,
    FULL;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

