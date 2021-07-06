package org.example.TaskTracker.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLongId {
    @JsonProperty("user_id")
    @JsonAlias({"id"})
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("project_id")
    @JsonAlias({"project"})
    private Long project;

    @JsonProperty("task_id")
    @JsonAlias({"task"})
    private Long task;

    public UserLongId() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "UserLongId{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project=" + project +
                '}';
    }
}
