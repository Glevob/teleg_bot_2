package ru.telgram.jokebot.model;

import ru.telgram.jokebot.model.UserRole;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link ru.telgram.jokebot.model.User}
 */

@Value
public class UserShab implements Serializable {
    Long id;
    String username;
    boolean enabled;
    Set<UserRole> roles;
}