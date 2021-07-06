INSERT INTO tasks
    (task_id, description, user_id, parent_task_id)
VALUES
    (1, 'Integrate database into the project and create all repositories and services.', 2, NULL),
    (2, 'Create business logic to work with the API.', 4, NULL),
    (3, 'Create reflection for data from API on basic Java objects.', 5, NULL),
    (4, 'Develop Web application using MVC pattern with the expectation of scaling.', 6, NULL),
    (5, 'Config Spring Boot and create controllers.', 8, 1),
    (6, 'Create entities and DTOs for it.', 7, 2),
    (7, 'Configure service.', 1, 3),
    (8, 'Write markup for application Views.', 3, 4),
    (9, 'Create all the required Models for the application.', 9, 4)
