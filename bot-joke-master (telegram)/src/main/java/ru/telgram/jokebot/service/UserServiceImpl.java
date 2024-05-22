package ru.telgram.jokebot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.telgram.jokebot.exceptions.*;
import ru.telgram.jokebot.model.*;
import ru.telgram.jokebot.repository.UserRepository;
import ru.telgram.jokebot.repository.UserRolesRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRolesRepository userRolesRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserShab registerUser(UserRegistrationShab shab) {
        if (shab == null) {
            throw new IncorrectUserException();
        }
        if (shab.getUsername() == null || shab.getPassword() == null) {
            throw new IncorrectUserException();
        }
        if (userRepository.findByUsername(shab.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        UserRole role = userRolesRepository.findByAuthority("USER").get();

        user.setUsername(shab.getUsername());
        user.setPassword(passwordEncoder.encode(shab.getPassword()));
        user.setEnabled(true);
        user.setRoles(new HashSet<>(List.of(role)));
        userRepository.save(user);
        return new UserShab(user.getId(), user.getUsername(), user.isEnabled(), user.getRoles());
    }


    @Override
    public UserShab grantRole(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<UserRole> optionalRole = userRolesRepository.findById(roleId);
        if (optionalUser.isPresent()) {
            if (optionalRole.isPresent()) {
                User user = optionalUser.get();
                UserRole role = optionalRole.get();
                user.getRoles().add(role);

                userRepository.save(user);
                return new UserShab(user.getId(), user.getUsername(), user.isEnabled(), user.getRoles());
            }
            else {
                throw new RoleNotFoundException();
            }
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public UserShab revokeRole(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<UserRole> optionalRole = userRolesRepository.findById(roleId);
        if (optionalUser.isPresent()) {
            if (optionalRole.isPresent()) {
                User user = optionalUser.get();
                UserRole role = optionalRole.get();

                if (!user.getRoles().contains(role)) {
                    throw new UserDoesNotHaveRoleException();
                }

                user.getRoles().remove(role);
                userRepository.save(user);
                return new UserShab(user.getId(), user.getUsername(), user.isEnabled(), user.getRoles());
            }
            else {
                throw new RoleNotFoundException();
            }
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public Page<User> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public List<UserRole> getAllRoles() {
        return userRolesRepository.findAll();
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
