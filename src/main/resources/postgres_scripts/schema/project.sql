CREATE TABLE projects(
    project_id SERIAL NOT NULL,
    description TEXT,
    CONSTRAINT PK_Project PRIMARY KEY(project_id)
)