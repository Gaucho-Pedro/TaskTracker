package org.example.TaskTracker.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskLongId {
    @JsonProperty("task_id")
    @JsonAlias({"id"})
    private Long id;

    @JsonProperty("description")
    @JsonAlias({"desc"})
    private String description;

    @JsonProperty(value = "user_id", access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias({"user", "executor"})
    private Long user;

    @JsonProperty("parent_task_id")
    @JsonAlias({"parent", "parent_task"})
    private Long parentTask;

    public TaskLongId() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getParentTask() {
        return parentTask;
    }

    public void setParentTask(Long parentTask) {
        this.parentTask = parentTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
