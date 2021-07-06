CREATE TABLE users(
    user_id SERIAL NOT NULL,
    name VARCHAR(100),
    project_id BIGINT,
    CONSTRAINT PK_User PRIMARY KEY(user_id),
    CONSTRAINT FK_project_id FOREIGN KEY(project_id) REFERENCES projects(project_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
)