package com.example.users;


import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UsersService {

    @Resource
    private UsersRepo usersRepo;

    public Iterable<User> getAll() {
        return usersRepo.findAll();
    }

    public User createUser(String userName) {
        User user = new User();
        user.setUserName(userName);
        user = usersRepo.save(user);
        return user;
    }

    public User getUserById(Long userId){
        return usersRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId " + userId));
    }

    public void delete(Long userId) {
        usersRepo.deleteById(userId);
    }

    public User update(Long userId, String newName) {
       User user = getUserById(userId);
        user.setUserName(newName);
        usersRepo.save(user);
        return user;
        }
    }

