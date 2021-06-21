DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS meal_seq;

CREATE SEQUENCE global_seq START WITH 100000;
CREATE SEQUENCE meal_seq START WITH 100000;


CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    --primary key = не будет двух юзеров с одинаковым айдишником
    name             VARCHAR                           NOT NULL,
    --VARCHAR = строка
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);
--говорит о том, что email у нас унмкальный
--(нельзя содзать двух пользователей с 1 и той же почтой)


CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    --OPTIONAL!!
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    --у 1 и того же юзера не может быть одинаковых ролей
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE meals
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id     INTEGER   NOT NULL,
    date_time   TIMESTAMP NOT NULL,
    description VARCHAR   NOT NULL,
    calories    INTEGER   NOT NULL,
    CONSTRAINT meals_date_time UNIQUE (user_id, date_time),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_date_time_idx ON meals (date_time);
CREATE INDEX meal_user_id_idx ON meals (user_id);