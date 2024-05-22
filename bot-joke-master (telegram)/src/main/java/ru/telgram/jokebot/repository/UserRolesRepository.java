package ru.telgram.jokebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.telgram.jokebot.model.UserRole;

import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByAuthority(String role);
}
