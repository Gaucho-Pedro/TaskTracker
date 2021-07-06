create table tasks(
    task_id SERIAL NOT NULL,
    description TEXT,
    user_id BIGINT,
    parent_task_id BIGINT,
    CONSTRAINT PK_Task PRIMARY KEY(task_id),
    CONSTRAINT FK_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT FK_parent_task_id FOREIGN KEY(parent_task_id) REFERENCES tasks(task_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)