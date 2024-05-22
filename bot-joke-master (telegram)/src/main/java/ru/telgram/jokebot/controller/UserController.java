package ru.telgram.jokebot.controller;

import ru.telgram.jokebot.model.UserShab;
import ru.telgram.jokebot.model.UserRegistrationShab;
import ru.telgram.jokebot.model.UserRole;
import ru.telgram.jokebot.model.User;
import ru.telgram.jokebot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // GET /users/getAll?page=num_page - получение всех пользователей с пагинацией
    @GetMapping("/getAll")
    ResponseEntity<Page<User>> getAllUsers(@RequestParam int page) {
        return ResponseEntity.ok(userService.getAllUsers(page));
    }

    // GET /users/getAllRoles - получение всех ролей
    @GetMapping("/getAllRoles")
    ResponseEntity<List<UserRole>> getAllRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    // POST /users/register - регистрация нового пользователя
    @PostMapping("/register")
    ResponseEntity<UserShab> registerUser(@RequestBody UserRegistrationShab shab) {
        return ResponseEntity.ok(userService.registerUser(shab));
    }

    // PUT /users/grant?userId=id&roleId=id - выдать роль пользователю
    @PutMapping("/grant")
    ResponseEntity<UserShab> grantRole(@RequestParam Long userId, @RequestParam Long roleId) {
        return ResponseEntity.ok(userService.grantRole(userId, roleId));
    }

    // PUT /users/revoke?userId=id&roleId=id - отозвать роль у пользователя
    @PutMapping("/revoke")
    ResponseEntity<UserShab> revokeRole(@RequestParam Long userId, @RequestParam Long roleId) {
        return ResponseEntity.ok(userService.revokeRole(userId, roleId));
    }

    // DELETE /users/userId - удаление пользователя
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}