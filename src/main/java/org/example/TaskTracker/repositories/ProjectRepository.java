package org.example.TaskTracker.repositories;


import org.example.TaskTracker.dto.ProjectLongId;
import org.example.TaskTracker.entities.Project;
import org.example.TaskTracker.entities.Task;
import org.example.TaskTracker.entities.User;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(ProjectLongId projectLongId) throws RequiredFieldMissingException {
        if (projectLongId.getDescription() == null)
            throw new RequiredFieldMissingException("description");
        jdbcTemplate.execute(
                "INSERT INTO PROJECTS(PROJECT_ID, DESCRIPTION) " +
                        "VALUES(" + projectLongId.getId() + ", '" + projectLongId.getDescription() + "')");
    }

    public ProjectLongId findById(Long id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM PROJECTS WHERE PROJECT_ID = " + id,
                (resultSet, rowNum) -> {
                    ProjectLongId res = new ProjectLongId();
                    res.setId(resultSet.getLong("PROJECT_ID"));
                    res.setDescription(resultSet.getString("DESCRIPTION"));
                    return res;
                });
    }

    public List<ProjectLongId> findAll() throws EmptyResultDataAccessException {
        return jdbcTemplate.query(
                "SELECT * FROM PROJECTS",
                (resultSet, rowNum) -> {
                    ProjectLongId res = new ProjectLongId();
                    res.setId(resultSet.getLong("PROJECT_ID"));
                    res.setDescription(resultSet.getString("DESCRIPTION"));
                    return res;
                });
    }

    public void update(ProjectLongId updatedProject) {
        Long id = updatedProject.getId();
        String description = updatedProject.getDescription();
        jdbcTemplate.execute(
                "UPDATE PROJECTS SET " +
                        "DESCRIPTION = " + (description != null ? "'" + description + "' " : "DESCRIPTION ") +
                        "WHERE PROJECT_ID = " + id);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM PROJECTS WHERE PROJECT_ID = " + id);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM PROJECTS");
    }

    public Project findFullById(Long id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM PROJECTS WHERE PROJECT_ID = " + id,
                (resultSet, rowNum) -> {
                    Project project = new Project();
                    project.setId(resultSet.getLong("PROJECT_ID"));
                    project.setDescription(resultSet.getString("DESCRIPTION"));
                    project.setUsers(jdbcTemplate.query(
                            "SELECT * FROM USERS WHERE PROJECT_ID = " + id,
                            (userResultSet, userRowNum) -> {
                                User user = new User();
                                user.setId(userResultSet.getLong("USER_ID"));
                                user.setName(userResultSet.getString("NAME"));
                                user.setTask(jdbcTemplate.queryForObject(
                                        "SELECT * FROM TASKS WHERE USER_ID = " + user.getId(),
                                        (taskResultSet, taskRowNum) -> {
                                            Task task = new Task();
                                            task.setId(taskResultSet.getLong("TASK_ID"));
                                            task.setDescription(taskResultSet.getString("DESCRIPTION"));
                                            return task;
                                        }
                                ));
                                return user;
                            }));
                    return project;
                });
    }

    public boolean existsById(Long id) {
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PROJECTS WHERE PROJECT_ID=" + id, Integer.class) != 0) {
            return true;
        }
        return false;
    }

}
