DROP DATABASE IF EXISTS libcat;
CREATE DATABASE IF NOT EXISTS libcat;
ALTER DATABASE libcat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE libcat;

CREATE TABLE author
(
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    fullname    VARCHAR(255) NOT NULL
);

CREATE TABLE book (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL
);

CREATE TABLE author_books (
    author_id   INTEGER(20) NOT NULL,
    book_id     INTEGER(20) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (author_id, book_id)
);

CREATE TABLE keyword (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(60) NOT NULL
);

CREATE TABLE book_keywords (
    book_id     INTEGER(20) NOT NULL,
    keyword_id  INTEGER(20) NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (keyword_id) REFERENCES keyword (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (book_id, keyword_id)
);

CREATE TABLE user (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(60) NOT NULL,
    email       VARCHAR(40) NOT NULL,
    password    VARCHAR(100) NOT NULL
);

CREATE TABLE role (
    id          INTEGER PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(60) NOT NULL
);

CREATE TABLE user_roles (
    user_id     INTEGER(20) NOT NULL,
    role_id     INTEGER(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (user_id, role_id)
);
