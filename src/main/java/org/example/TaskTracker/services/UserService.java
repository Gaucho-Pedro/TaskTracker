package org.example.TaskTracker.services;



import org.example.TaskTracker.dto.UserLongId;
import org.example.TaskTracker.entities.User;
import org.example.TaskTracker.repositories.UserRepository;
import org.example.TaskTracker.support.InvalidIdentifierException;
import org.example.TaskTracker.support.PrimaryKeyViolationException;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAllUsers(UserLongId[] usersLongId) throws PrimaryKeyViolationException, RequiredFieldMissingException {
        for (UserLongId userLongId : usersLongId)
            createUser(userLongId);
    }

    public void createUser(UserLongId userLongId) throws PrimaryKeyViolationException, RequiredFieldMissingException {
        Long id = userLongId.getId();
        if (id != null && userRepository.existsById(id))
            throw new PrimaryKeyViolationException("User", id);
        userRepository.create(userLongId);
    }

    public UserLongId getUserById(Long id) throws InvalidIdentifierException {
        if (!userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        return userRepository.findById(id);
    }

    public User fullInfo(Long id) throws InvalidIdentifierException {
        if (!userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        return userRepository.findFullById(id);
    }
    public List<UserLongId> getAllUsers() {
        return userRepository.findAll();
    }
    public void updateAllUsers(UserLongId[] usersLongId) throws InvalidIdentifierException {
        for (UserLongId userLongId : usersLongId)
            updateUser(userLongId);
    }

    public void updateUser(UserLongId userLongId) throws InvalidIdentifierException {
        Long id = userLongId.getId();
        if (id == null || !userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        userRepository.update(userLongId);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteUserById(Long id) throws InvalidIdentifierException {
        if (!userRepository.existsById(id))
            throw new InvalidIdentifierException("User", id);
        userRepository.deleteById(id);
    }
}