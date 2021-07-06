package org.example.TaskTracker.controllers;

import org.example.TaskTracker.dto.TaskLongId;
import org.example.TaskTracker.services.TaskService;
import org.example.TaskTracker.support.InvalidIdentifierException;
import org.example.TaskTracker.support.PrimaryKeyViolationException;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.example.TaskTracker.support.WrongParentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping("/task")
    public Object doPost(@RequestBody TaskLongId[] tasksLongId) throws PrimaryKeyViolationException, RequiredFieldMissingException, WrongParentException {
        service.createAllTasks(tasksLongId);
        return new Status(true, null);
    }

    @GetMapping("/task/{id}")
    public Object doGet(@PathVariable("id") Long id,
                        @RequestParam(value = "full", defaultValue = "false") boolean full) throws InvalidIdentifierException {
        if (full)
            return service.fullInfo(id);
        else
            return service.getTaskById(id);
    }

    @GetMapping("/task")
    public Object doGet() {
        return service.getAllTasks();
    }

    @PutMapping("/task")
    public Object doPut(@RequestBody TaskLongId[] tasksLongId) throws InvalidIdentifierException, WrongParentException {
        service.updateAllTasks(tasksLongId);
        return new Status(true, null);
    }

    @DeleteMapping("/task/{id}")
    public Object doDelete(@PathVariable("id") Long id) throws InvalidIdentifierException {
        service.deleteTaskById(id);
        return new Status(true, null);
    }

    @DeleteMapping("/task")
    public Object doDelete() {
        service.deleteAllTasks();
        return new Status(true, null);
    }

    @ExceptionHandler(Exception.class)
    public Object GeneralHandlerException(Exception exception) {
        return new Status(false, exception.getMessage());
    }
}
