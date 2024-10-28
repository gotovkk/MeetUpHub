-- Database: meetuphub

DROP TABLE IF EXISTS meetuphub;

CREATE DATABASE meetuphub;
CREATE TABLE "user"
(
    id            INTEGER             NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY,
    name          VARCHAR(255)        NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE category
(
    id          INTEGER      NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE event
(
    id           INTEGER      NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    status       VARCHAR(255),
    start_time   TIMESTAMP    NOT NULL,
    end_time     TIMESTAMP    NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    location_id  INTEGER,
    organizer_id INTEGER REFERENCES "user" (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
CREATE TABLE event_category
(
    event_id    INTEGER NOT NULL REFERENCES event (id) ON DELETE CASCADE,
    category_id INTEGER NOT NULL REFERENCES category (id) ON DELETE CASCADE,
    PRIMARY KEY (event_id, category_id)
);

CREATE TABLE location
(
    id          INTEGER      NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE event_user
(
    user_id       INTEGER NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    event_id      INTEGER NOT NULL REFERENCES event (id) ON DELETE CASCADE,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status        VARCHAR(255),
    PRIMARY KEY (user_id, event_id)
);

CREATE TABLE location_category
(
    location_id INTEGER NOT NULL REFERENCES location (id) ON DELETE CASCADE,
    category_id INTEGER NOT NULL REFERENCES category (id) ON DELETE CASCADE,
    PRIMARY KEY (location_id, category_id)
);


CREATE TABLE tag
(
    id   INTEGER NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY,
    name TEXT    NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE event_tag
(
    event_id INTEGER NOT NULL REFERENCES event (id) ON DELETE CASCADE,
    tag_id   INTEGER NOT NULL REFERENCES tag (id) ON DELETE CASCADE,
    PRIMARY KEY (event_id, tag_id)
);


CREATE TABLE role
(
    id   INTEGER      NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES role (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);