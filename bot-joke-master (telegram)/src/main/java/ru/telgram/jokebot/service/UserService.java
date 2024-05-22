package ru.telgram.jokebot.service;

import org.springframework.data.domain.Page;
import ru.telgram.jokebot.model.User;
import ru.telgram.jokebot.model.UserRegistrationShab;
import ru.telgram.jokebot.model.UserRole;
import ru.telgram.jokebot.model.UserShab;

import java.util.List;

public interface UserService {
    Page<User> getAllUsers(int page);
    List<UserRole> getAllRoles();
    UserShab registerUser(UserRegistrationShab shab);
    UserShab grantRole(Long userId, Long roleId);
    UserShab revokeRole(Long userId, Long roleId);
    void deleteUser(Long userId);
}
