package org.example.TaskTracker.repositories;



import org.example.TaskTracker.dto.TaskLongId;
import org.example.TaskTracker.entities.Project;
import org.example.TaskTracker.entities.Task;
import org.example.TaskTracker.entities.User;
import org.example.TaskTracker.support.RequiredFieldMissingException;
import org.example.TaskTracker.support.WrongParentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(TaskLongId taskLongId) throws WrongParentException, RequiredFieldMissingException {
        if (taskLongId.getId() != null && taskLongId.getParentTask().equals(taskLongId.getId()))
            throw new WrongParentException(taskLongId.getId(), taskLongId.getParentTask());
        if (taskLongId.getDescription() == null)
            throw new RequiredFieldMissingException("description");
        jdbcTemplate.execute(
                "INSERT INTO TASKS(TASK_ID,DESCRIPTION,USER_ID,PARENT_TASK_ID) " +
                        "VALUES(" + taskLongId.getId() + ", '" + taskLongId.getDescription() + "', " + taskLongId.getUser() + ","
                        + taskLongId.getParentTask() + ")");
    }

    public TaskLongId findById(Long id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM TASKS WHERE TASK_ID = " + id,
                (resultSet, rowNum) -> {
                    TaskLongId res = new TaskLongId();
                    res.setId(resultSet.getLong("TASK_ID"));
                    res.setDescription(resultSet.getString("DESCRIPTION"));
                    return res;
                });
    }

    public Task findFullById(Long id) {
        return jdbcTemplate.queryForObject("SELECT tasks.task_id,tasks.description as task_desc," +
                        "tasks.parent_task_id,users.user_id,users.name,users.project_id, projects.description " +
                        "as project_desc FROM tasks JOIN users on tasks.user_id = users.user_id join projects " +
                        "on users.project_id=projects.project_id where task_id=" + id,
                (resultSet, rowNum) -> {
                    Task res = new Task();
                    User user = new User();
                    Project project = new Project();
                    Task task = new Task();

                    res.setProject(project);
                    res.setParentTask(task);
                    res.setUser(user);

                    res.setId(resultSet.getLong("TASK_ID"));
                    res.setDescription(resultSet.getString("TASK_DESC"));
                    res.getParentTask().setId(resultSet.getLong("PARENT_TASK_ID"));
                    res.getParentTask().setDescription(jdbcTemplate.queryForObject("SELECT DESCRIPTION from tasks where Task_id=" + res.getParentTask().getId(), String.class));
                    res.getUser().setId(resultSet.getLong("USER_ID"));
                    res.getUser().setName(resultSet.getString("NAME"));
                    res.getProject().setId(resultSet.getLong("PROJECT_ID"));
                    res.getProject().setDescription(resultSet.getString("PROJECT_DESC"));
                    return res;
                });
    }

    public List<TaskLongId> findAll() throws EmptyResultDataAccessException {
        return jdbcTemplate.query(
                "SELECT * FROM TASKS",
                (resultSet, rowNum) -> {
                    TaskLongId res = new TaskLongId();
                    res.setId(resultSet.getLong("TASK_ID"));
                    res.setDescription(resultSet.getString("DESCRIPTION"));
                    return res;
                });
    }

    public void update(TaskLongId task) throws WrongParentException {
        if (task.getId() != null && task.getParentTask().equals(task.getId()))
            throw new WrongParentException(task.getId(), task.getParentTask());
        Long taskId = task.getId();
        Long userId = task.getUser();
        Long parentId = task.getParentTask();
        String description = task.getDescription();
        jdbcTemplate.execute(
                "UPDATE TASKS SET " +
                        "DESCRIPTION = " + (description != null ? "'" + description + "' " : "DESCRIPTION ") +
                        ",USER_ID = " + (userId != null ? userId : "USER_ID ") +
                        ",PARENT_TASK_ID = " + (parentId != null ? parentId : "PARENT_TASK_ID ") +
                        " WHERE TASK_ID = " + taskId);
    }

    public void deleteById(Long id) {
        jdbcTemplate.execute("DELETE FROM TASKS WHERE TASK_ID = " + id);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM TASKS");
    }

    public boolean existsById(Long id) {
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TASKS WHERE TASK_ID = " + id, Long.class) != 0) {
            return true;
        }
        return false;
    }
}
