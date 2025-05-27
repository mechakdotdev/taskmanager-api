CREATE TABLE IF NOT EXISTS users
(
    id
    BIGINT
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    capacity INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS projects
(
    id
    BIGINT
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    owner_id BIGINT,
    FOREIGN KEY
(
    owner_id
) REFERENCES users
(
    id
)
    );

CREATE TABLE IF NOT EXISTS tasks
(
    id
    BIGINT
    PRIMARY
    KEY,
    title
    VARCHAR
(
    255
) NOT NULL,
    priority VARCHAR
(
    20
) NOT NULL,
    estimated_duration INT NOT NULL,
    deadline TIMESTAMP NOT NULL,
    project_id BIGINT,
    assigned_user_id BIGINT,
    FOREIGN KEY
(
    project_id
) REFERENCES projects
(
    id
),
    FOREIGN KEY
(
    assigned_user_id
) REFERENCES users
(
    id
)
    );