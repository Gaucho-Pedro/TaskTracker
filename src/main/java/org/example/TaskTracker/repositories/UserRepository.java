package org.example.TaskTracker.repositories;


import org.example.TaskTracker.dto.UserLongId;
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
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(UserLongId userLongId) throws RequiredFieldMissingException {
        if (userLongId.getName() == null)
            throw new RequiredFieldMissingException("name");
        jdbcTemplate.execute(
                "INSERT INTO USERS(USER_ID,NAME,PROJECT_ID) " +
                        "VALUES(" + userLongId.getId() + ", '" + userLongId.getName() + "'," + userLongId.getProject() + ")");

    }

    public UserLongId findById(Long id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM USERS WHERE USER_ID = " + id,
                (resultSet, rowNum) -> {
                    UserLongId res = new UserLongId();
                    res.setId(resultSet.getLong("USER_ID"));
                    res.setName(resultSet.getString("NAME"));
                    return res;
                });
    }

    public User findFullById(Long id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT USERS.USER_ID,USERS.NAME,USERS.PROJECT_ID," +
                        "PROJECTS.DESCRIPTION AS PROJECT_DESC,TASKS.TASK_ID,TASKS.DESCRIPTION AS TASK_DESC " +
                        "FROM USERS JOIN PROJECTS ON USERS.PROJECT_ID = PROJECTS.PROJECT_ID JOIN TASKS " +
                        "ON USERS.USER_ID = TASKS.USER_ID WHERE USERS.USER_ID=" + id,
                (resultSet, rowNum) -> {
                    User res = new User();
                    Project project = new Project();
                    Task task = new Task();
                    res.setProject(project);
                    res.setTask(task);

                    res.setId(resultSet.getLong("USER_ID"));
                    res.setName(resultSet.getString("NAME"));
                    res.getProject().setId(resultSet.getLong("PROJECT_ID"));
                    res.getProject().setDescription(resultSet.getString("PROJECT_DESC"));
                    res.getTask().setId(resultSet.getLong("TASK_ID"));
                    res.getTask().setDescription(resultSet.getString("TASK_DESC"));
                    return res;
                });
    }
    public List<UserLongId> findAll() throws EmptyResultDataAccessException {
        return jdbcTemplate.query(
                "SELECT * FROM USERS",
                (resultSet, rowNum) -> {
                    UserLongId res = new UserLongId();
                    res.setId(resultSet.getLong("USER_ID"));
                    res.setName(resultSet.getString("NAME"));
                    return res;
                });
    }

    public void update(UserLongId user) {
        Long userId = user.getId();
        Long projectId = user.getProject();
        jdbcTemplate.execute(
                "UPDATE USERS SET " +
                        "PROJECT_ID = " + (projectId != null ? projectId : "PROJECT_ID ") +
                        "WHERE USER_ID = " + userId);
    }

    public void deleteById(Long id) {
        jdbcTemplate.execute("DELETE FROM USERS WHERE USER_ID = " + id);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM USERS");
    }

//    public Long count(Long id) throws EmptyResultDataAccessException {
//        return jdbcTemplate.queryForObject(
//                "SELECT PROJECT_ID,COUNT(USER_ID) FROM USERS WHERE PROJECT_ID = " + id + " GROUP BY PROJECT_ID",
//                (resultSet, rowNum) -> {
//                    return resultSet.getLong("COUNT");
//                });
//    }

    public boolean existsById(Long id) {
        if (jdbcTemplate.queryForObject("SELECT COUNT(*) from users where user_id=" + id, Integer.class) != 0) {
            return true;
        }
        return false;
    }
}
