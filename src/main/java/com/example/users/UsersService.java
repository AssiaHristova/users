package com.example.users;


import com.amazonaws.services.codebuild.model.ResourceAlreadyExistsException;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UsersService {

    @Resource
    private UsersRepo usersRepo;

    private boolean existsById(Long id) {
        return usersRepo.existsById(id);
    }

    public User findById(Long id) throws ResourceNotFoundException {
        User user = usersRepo.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("Cannot find User with id: " + id);
        } else return user;
    }

    public Iterable<User> findAll() {
        return usersRepo.findAll();
    }

    public User save(User user) throws ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(user.getUserName())) {
            if (user.getUserId() != null && existsById(user.getUserId())) {
                throw new ResourceAlreadyExistsException("User with id: " + user.getUserId() +
                        " already exists");
            }
            return usersRepo.save(user);
        }
        return usersRepo.save(user);
    }

    public void update(User user)
            throws ResourceNotFoundException {
        if (!StringUtils.isEmpty(user.getUserName())) {
            if (!existsById(user.getUserId())) {
                throw new ResourceNotFoundException("Cannot find User with id: " + user.getUserId());
            }
            usersRepo.save(user);
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find User with id: " + id);
        } else {
            usersRepo.deleteById(id);
        }
    }


}

