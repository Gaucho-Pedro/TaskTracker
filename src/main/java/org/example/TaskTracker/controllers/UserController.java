package org.example.TaskTracker.controllers;

import org.example.TaskTracker.dto.UserLongId;
import org.example.TaskTracker.services.UserService;
import org.example.TaskTracker.support.InvalidIdentifierException;
import org.example.TaskTracker.support.PrimaryKeyViolationException;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/user")
    public Object doPost(@RequestBody UserLongId[] usersLongId) throws PrimaryKeyViolationException, RequiredFieldMissingException {
        service.createAllUsers(usersLongId);
        return new Status(true, null);
    }

    @GetMapping("/user/{id}")
    public Object doGet(@PathVariable("id") Long id,
                        @RequestParam(value = "full", defaultValue = "false") boolean full) throws InvalidIdentifierException {
        if (full)
            return service.fullInfo(id);
        else
            return service.getUserById(id);
    }
    @GetMapping("/user")
    public Object doGet() {
        return service.getAllUsers();
    }
    @PutMapping("/user")
    public Object doPut(@RequestBody UserLongId[] usersLongId) throws InvalidIdentifierException {
        service.updateAllUsers(usersLongId);
        return new Status(true, null);
    }

    @DeleteMapping("/user/{id}")
    public Object doDelete(@PathVariable("id") Long id) throws InvalidIdentifierException {
        service.deleteUserById(id);
        return new Status(true, null);
    }

    @DeleteMapping("/user")
    public Object doDelete() {
        service.deleteAllUsers();
        return new Status(true, null);
    }

    @ExceptionHandler(Exception.class)
    public Object GeneralHandlerException(Exception exception) {
        return new Status(false, exception.getMessage());
    }
}
