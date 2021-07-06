package org.example.TaskTracker.controllers;

import org.example.TaskTracker.dto.ProjectLongId;
import org.example.TaskTracker.services.ProjectService;
import org.example.TaskTracker.support.InvalidIdentifierException;
import org.example.TaskTracker.support.PrimaryKeyViolationException;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService service;


    @PostMapping("/project")
    public Object doPost(@RequestBody ProjectLongId[] projectsLongId) throws PrimaryKeyViolationException, RequiredFieldMissingException {
        service.createAllProjects(projectsLongId);
        return new Status(true, null);
    }


    @GetMapping("/project/{id}")
    public Object doGet(@PathVariable("id") Long id,
                        @RequestParam(value = "full", defaultValue = "false") boolean full) throws InvalidIdentifierException {
        if (full)
            return service.fullInfo(id);
        else
            return service.getProjectById(id);
    }

    @GetMapping("/project")
    public Object doGet(){
        return service.getAllProjects();
    }

    @PutMapping("/project")
    public Object doPut(@RequestBody ProjectLongId[] projectsLongId) throws InvalidIdentifierException {
        service.updateAllProjects(projectsLongId);
        return new Status(true, null);
    }

    @DeleteMapping("/project/{id}")
    public Object doDelete(@PathVariable("id") Long id) throws InvalidIdentifierException {
        service.deleteProjectById(id);
        return new Status(true, null);
    }

    @DeleteMapping("/project")
    public Object doDelete() {
        service.deleteAllProjects();
        return new Status(true, null);
    }

    @ExceptionHandler(Exception.class)
    public Object GeneralHandlerException(Exception exception) {
        return new Status(false, exception.getMessage());
    }
}
