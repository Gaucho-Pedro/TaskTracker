package org.example.TaskTracker.services;

import org.example.TaskTracker.dto.ProjectLongId;
import org.example.TaskTracker.entities.Project;
import org.example.TaskTracker.repositories.ProjectRepository;
import org.example.TaskTracker.support.InvalidIdentifierException;
import org.example.TaskTracker.support.PrimaryKeyViolationException;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public void createAllProjects(ProjectLongId[] projectsLongId) throws PrimaryKeyViolationException, RequiredFieldMissingException {
        for (ProjectLongId projectLongId : projectsLongId)
            createProject(projectLongId);
    }

    public void createProject(ProjectLongId projectLongId) throws PrimaryKeyViolationException, RequiredFieldMissingException {
        Long id = projectLongId.getId();
        if (id != null && projectRepository.existsById(id))
            throw new PrimaryKeyViolationException("Project", id);
        projectRepository.create(projectLongId);
    }


    public ProjectLongId getProjectById(Long id) throws InvalidIdentifierException {
        if (!projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        return projectRepository.findById(id);
    }

    public List<ProjectLongId> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project fullInfo(Long id) throws InvalidIdentifierException {
        if (!projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        return projectRepository.findFullById(id);
    }

    public void updateAllProjects(ProjectLongId[] projectsLongId) throws InvalidIdentifierException {
        for (ProjectLongId projectLongId : projectsLongId)
            updateProject(projectLongId);
    }

    public void updateProject(ProjectLongId projectLongId) throws InvalidIdentifierException {
        Long id = projectLongId.getId();
        if (id == null || !projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        projectRepository.update(projectLongId);
    }

    public void deleteAllProjects() {
        projectRepository.deleteAll();
    }

    public void deleteProjectById(Long id) throws InvalidIdentifierException {
        if (!projectRepository.existsById(id))
            throw new InvalidIdentifierException("Project", id);
        projectRepository.deleteById(id);
    }
}
