package org.example.TaskTracker.services;



import org.example.TaskTracker.dto.TaskLongId;
import org.example.TaskTracker.entities.Task;
import org.example.TaskTracker.repositories.TaskRepository;
import org.example.TaskTracker.support.InvalidIdentifierException;
import org.example.TaskTracker.support.PrimaryKeyViolationException;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.example.TaskTracker.support.WrongParentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createAllTasks(TaskLongId[] tasksLongId) throws PrimaryKeyViolationException, WrongParentException, RequiredFieldMissingException {
        for (TaskLongId taskLongId : tasksLongId)
            createTask(taskLongId);
    }

    public void createTask(TaskLongId taskLongId) throws PrimaryKeyViolationException, WrongParentException, RequiredFieldMissingException {
        Long id = taskLongId.getId();
        if (id != null && taskRepository.existsById(id))
            throw new PrimaryKeyViolationException("Task", id);
        taskRepository.create(taskLongId);
    }


    public TaskLongId getTaskById(Long id) throws InvalidIdentifierException {
        if (!taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        return taskRepository.findById(id);
    }

    public Task fullInfo(Long id) throws InvalidIdentifierException {
        if(!taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        return taskRepository.findFullById(id);
    }

    public List<TaskLongId> getAllTasks() {
        return taskRepository.findAll();
    }
    public void updateAllTasks(TaskLongId[] tasksLongId) throws InvalidIdentifierException, WrongParentException {
        for (TaskLongId taskLongId : tasksLongId)
            updateTask(taskLongId);
    }

    public void updateTask(TaskLongId taskLongId) throws InvalidIdentifierException, WrongParentException {
        Long id = taskLongId.getId();
        if (id == null || !taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        taskRepository.update(taskLongId);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    public void deleteTaskById(Long id) throws InvalidIdentifierException {
        if (!taskRepository.existsById(id))
            throw new InvalidIdentifierException("Task", id);
        taskRepository.deleteById(id);
    }
}
