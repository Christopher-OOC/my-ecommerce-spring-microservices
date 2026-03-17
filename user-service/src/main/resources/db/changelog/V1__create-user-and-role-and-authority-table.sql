--liquibase formatted sql
--changeset javalord:1

CREATE TABLE IF NOT EXISTS authorities (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(100) NOT NULL UNIQUE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP,
                             created_by VARCHAR(100),
                             updated_by VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP,
                       created_by VARCHAR(100),
                       updated_by VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP,
                       created_by VARCHAR(100),
                       updated_by VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_roles (
                            user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS role_authorities (
                                  role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                                  authority_id BIGINT NOT NULL REFERENCES authorities(id) ON DELETE CASCADE,
                                  PRIMARY KEY (role_id, authority_id)
);