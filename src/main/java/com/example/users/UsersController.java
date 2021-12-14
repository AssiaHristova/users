package com.example.users;

import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        Iterable<User> contacts = usersService.findAll();
        return "all-users";
    }

    @GetMapping("/users/{userId}")
    public String getUserById(Model model, @PathVariable Long userId) {
        User user = null;
        try {
            user = usersService.findById(userId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "User not found");
        }
        model.addAttribute("user", user);
        return "get-user";
    }

    @GetMapping("/users/add")
    public String showAddUser(Model model) {
        User user = new User();
        model.addAttribute("add", true);
        model.addAttribute("user", user);
        return "create-form";
    }

    @PostMapping("/users/add")
    public String addUser(Model model, @ModelAttribute("user") User user) {
            try {
                User newUser = usersService.save(user);
                return "redirect:/all-users/";
            } catch (Exception ex) {
                String errorMessage = ex.getMessage();
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("add", true);
                return "user-edit";
            }
    }

    @GetMapping("/users/{UserId}/edit")
    public String showEditUser(Model model, @PathVariable Long userId) {
        User user = null;
        try {
            user = usersService.findById(userId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Contact not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("user", user);
        return "edit-form";
    }

    @PostMapping("/users/{userId}/edit")
    public String updateUser(Model model, @PathVariable Long userId, @ModelAttribute("user") User user) {
        try {
            user.setUserId(userId);
            usersService.update(user);
            return "redirect:/all-users/" + String.valueOf(user.getUserId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "edit-form";
        }
    }

    @GetMapping("/users/{userId}/delete")
    public String showDeleteUserById(Model model, @PathVariable Long userId) {
            User user = null;
            try {
                user = usersService.findById(userId);
            } catch (ResourceNotFoundException ex) {
                model.addAttribute("errorMessage", "User not found");
            }
            model.addAttribute("allowDelete", true);
            model.addAttribute("user", user);
            return "get-user";
        }

    @DeleteMapping("/users/{userId}/delete")
    public String deleteUserById(
            Model model, @PathVariable long userId) {
        try {
            usersService.deleteById(userId);
            return "redirect:/all-users";
        } catch (ResourceNotFoundException ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "get-user";
        }
}}
