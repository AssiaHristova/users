package com.example.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String home() {
        return "Hello Users";
    }
    @GetMapping("/users")
    public Iterable<User> getAll() {
        return usersService.getAll();
    }
    @PostMapping("/create")
    public User create(String userName) {
        return usersService.createUser(userName);
    }
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable Long userId) {
        return usersService.getUserById(userId);
    }
    @PostMapping("/update/{userId}")
    public User update(@PathVariable Long userId, String userName) {
        return usersService.update(userId, userName);
    }
    @PostMapping("/delete/{userId}")
    public void delete(@PathVariable Long userId) {
        usersService.delete(userId);
    }
}
