CREATE SCHEMA IF NOT EXISTS harvest;

-- SET NAMES 'UTF8MB4';
-- SET
-- TIME_ZONE 'Europe/Riga';
-- SET
-- TIME_ZONE = '+3:00';

USE harvest;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) DEFAULT NULL,
    enabled    BOOLEAN DEFAULT FALSE,
    non_locked BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    image_url  VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS Give;

CREATE TABLE Give (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      date DATETIME DEFAULT CURRENT_TIMESTAMP,
                      type VARCHAR(255) NOT NULL,
                      amount DOUBLE NOT NULL,
                      amount_type VARCHAR(10) NOT NULL,
                      description TEXT,
                      img_url  VARCHAR(255),
                      location VARCHAR(500),
                      status VARCHAR(255) NOT NULL,
                      users_give_id BIGINT UNSIGNED NOT NULL,
                      FOREIGN KEY (users_give_id) REFERENCES Users(id)
);
DROP TABLE IF EXISTS GardeningPost;

CREATE TABLE GardeningPost (
                      id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      date DATETIME DEFAULT CURRENT_TIMESTAMP,
                      title VARCHAR(60) NOT NULL,
                      description TEXT,
                      tag VARCHAR(50),
                      likes INT,
                      view_count INT,
                      img_url  VARCHAR(255),
                      users_gardening_post_id BIGINT UNSIGNED NOT NULL,
                      FOREIGN KEY (users_gardening_post_id) REFERENCES Users(id)
);

DROP TABLE IF EXISTS GardeningPostLikes;

CREATE TABLE GardeningPostLikes (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           user_id BIGINT UNSIGNED NOT NULL,
                           post_id BIGINT UNSIGNED NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES Users(id),
                           FOREIGN KEY (post_id) REFERENCES GardeningPost(id)
);



DROP TABLE IF EXISTS GardeningComment;

CREATE TABLE GardeningComment (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               comment_text TEXT,
                               likes INT,
                               parent_comment_id BIGINT,
                               comment_user_id BIGINT UNSIGNED NOT NULL,
                            comment_gardening_post_id BIGINT UNSIGNED NOT NULL,
                               FOREIGN KEY (comment_user_id) REFERENCES Users(id),
                               FOREIGN KEY (comment_gardening_post_id) REFERENCES GardeningPost(id)
);

DROP TABLE IF EXISTS GardeningCommentLikes;

CREATE TABLE GardeningCommentLikes (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              user_id BIGINT UNSIGNED NOT NULL,
                              comment_id BIGINT NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES Users(id),
                              FOREIGN KEY (comment_id) REFERENCES GardeningComment(id)
);


DROP TABLE IF EXISTS RecipePost;

CREATE TABLE RecipePost (
                            id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               title VARCHAR(60) NOT NULL,
                               description TEXT,
                               tag VARCHAR(50),
                               likes INT,
                               view_count INT,
                               img_url  VARCHAR(255),
                               users_recipe_post_id BIGINT UNSIGNED NOT NULL,
                               FOREIGN KEY (users_recipe_post_id) REFERENCES Users(id)
);

DROP TABLE IF EXISTS RecipePostLikes;

CREATE TABLE RecipePostLikes (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    user_id BIGINT UNSIGNED NOT NULL,
                                    post_id BIGINT UNSIGNED NOT NULL,
                                    FOREIGN KEY (user_id) REFERENCES Users(id),
                                    FOREIGN KEY (post_id) REFERENCES RecipePost(id)
);

DROP TABLE IF EXISTS RecipeComment;

CREATE TABLE RecipeComment (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               comment_text TEXT,
                               likes INT,
                               parent_comment_id BIGINT,
                               comment_user_id BIGINT UNSIGNED NOT NULL,
                               comment_recipe_post_id BIGINT UNSIGNED NOT NULL,
                               FOREIGN KEY (comment_user_id) REFERENCES Users(id),
                               FOREIGN KEY (comment_recipe_post_id) REFERENCES RecipePost(id)
);

DROP TABLE IF EXISTS RecipeCommentLikes;

CREATE TABLE RecipeCommentLikes (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       user_id BIGINT UNSIGNED NOT NULL,
                                       comment_id BIGINT NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES Users(id),
                                       FOREIGN KEY (comment_id) REFERENCES RecipeComment(id)
);

DROP TABLE IF EXISTS IMadePost;

CREATE TABLE IMadePost (
                           id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            title VARCHAR(60) NOT NULL,
                            description TEXT,
                            tag VARCHAR(50),
                            likes INT,
                            view_count INT,
                            img_url  VARCHAR(255),
                            users_i_made_post_id BIGINT UNSIGNED NOT NULL,
                            FOREIGN KEY (users_i_made_post_id) REFERENCES Users(id)
);

DROP TABLE IF EXISTS IMadePostLikes;

CREATE TABLE IMadePostLikes (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 user_id BIGINT UNSIGNED NOT NULL,
                                 post_id BIGINT UNSIGNED NOT NULL,
                                 FOREIGN KEY (user_id) REFERENCES Users(id),
                                 FOREIGN KEY (post_id) REFERENCES IMadePost(id)
);

DROP TABLE IF EXISTS IMadeComment;

CREATE TABLE IMadeComment (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               date DATETIME DEFAULT CURRENT_TIMESTAMP,
                               comment_text TEXT,
                               likes INT,
                               parent_comment_id BIGINT,
                               comment_user_id BIGINT UNSIGNED NOT NULL,
                               comment_i_made_post_id BIGINT UNSIGNED NOT NULL,
                               FOREIGN KEY (comment_user_id) REFERENCES Users(id),
                               FOREIGN KEY (comment_i_made_post_id) REFERENCES IMadePost(id)
);

DROP TABLE IF EXISTS IMadeCommentLikes;

CREATE TABLE IMadeCommentLikes (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    user_id BIGINT UNSIGNED NOT NULL,
                                    comment_id BIGINT NOT NULL,
                                    FOREIGN KEY (user_id) REFERENCES Users(id),
                                    FOREIGN KEY (comment_id) REFERENCES IMadeComment(id)
);

DROP TABLE IF EXISTS OtherPost;

CREATE TABLE OtherPost (
                           id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           date DATETIME DEFAULT CURRENT_TIMESTAMP,
                           title VARCHAR(60) NOT NULL,
                           description TEXT,
                           tag VARCHAR(50),
                           likes INT,
                           view_count INT,
                           img_url  VARCHAR(255),
                           users_other_post_id BIGINT UNSIGNED NOT NULL,
                           FOREIGN KEY (users_other_post_id) REFERENCES Users(id)
);

DROP TABLE IF EXISTS OtherPostLikes;

CREATE TABLE OtherPostLikes (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                user_id BIGINT UNSIGNED NOT NULL,
                                post_id BIGINT UNSIGNED NOT NULL,
                                FOREIGN KEY (user_id) REFERENCES Users(id),
                                FOREIGN KEY (post_id) REFERENCES OtherPost(id)
);

DROP TABLE IF EXISTS OtherComment;

CREATE TABLE OtherComment (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              date DATETIME DEFAULT CURRENT_TIMESTAMP,
                              comment_text TEXT,
                              likes INT,
                              parent_comment_id BIGINT,
                              comment_user_id BIGINT UNSIGNED NOT NULL,
                              comment_other_post_id BIGINT UNSIGNED NOT NULL,
                              FOREIGN KEY (comment_user_id) REFERENCES Users(id),
                              FOREIGN KEY (comment_other_post_id) REFERENCES OtherPost(id)
);

DROP TABLE IF EXISTS OtherCommentLikes;

CREATE TABLE OtherCommentLikes (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   user_id BIGINT UNSIGNED NOT NULL,
                                   comment_id BIGINT NOT NULL,
                                   FOREIGN KEY (user_id) REFERENCES Users(id),
                                   FOREIGN KEY (comment_id) REFERENCES OtherComment(id)
);

DROP TABLE IF EXISTS Roles;

CREATE TABLE Roles
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    permission VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

DROP TABLE IF EXISTS UserRoles;

CREATE TABLE UserRoles
(
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    role_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

DROP TABLE IF EXISTS Events;

CREATE TABLE Events
(
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50) NOT NULL CHECK(type IN ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS', 'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE', 'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE')),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);

DROP TABLE IF EXISTS UserEvents;

CREATE TABLE UserEvents
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNSIGNED NOT NULL,
    event_id   BIGINT UNSIGNED NOT NULL,
    device     VARCHAR(100) DEFAULT NULL,
    ip_address VARCHAR(100) DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS AccountVerifications;

CREATE TABLE AccountVerifications
(
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    url     VARCHAR(255) NOT NULL,
    -- date     DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url)
);

DROP TABLE IF EXISTS ResetPasswordVerifications;

CREATE TABLE ResetPasswordVerifications
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT UNSIGNED NOT NULL,
    url             VARCHAR(255) NOT NULL,
    expiration_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_ResetPasswordVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_ResetPasswordVerifications_Url UNIQUE (url)
);

DROP TABLE IF EXISTS TwoFactorVerifications;

CREATE TABLE TwoFactorVerifications
(
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT UNSIGNED NOT NULL,
    code            VARCHAR(10) NOT NULL,
    expiration_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_TwoFactorVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_TwoFactorVerifications_Code UNIQUE (code)
);


