package ru.telgram.jokebot.model;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.telgram.jokebot.model.User}
 */

@Value
public class UserRegistrationShab implements Serializable {
    Long id;
    String username;
    String password;
}