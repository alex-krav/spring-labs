USE libcat;

INSERT INTO author VALUES (1, 'Джордж Оруэлл');
INSERT INTO author VALUES (2, 'Людмила Сарычева');
INSERT INTO author VALUES (3, 'Максим Ильяхов');

INSERT INTO book VALUES (1, '1984');
INSERT INTO book VALUES (2, 'Пиши, скорочуй. Як створити дієвий текст');

INSERT INTO author_books VALUES (1, 1);
INSERT INTO author_books VALUES (2, 2);
INSERT INTO author_books VALUES (3, 2);

INSERT INTO keyword VALUES (1, 'фентези');
INSERT INTO keyword VALUES (2, 'фантастика');
INSERT INTO keyword VALUES (3, 'aнтиутопия');
INSERT INTO keyword VALUES (4, 'Саморазвитие');
INSERT INTO keyword VALUES (5, 'Мотивация');
INSERT INTO keyword VALUES (6, 'Креативность');
INSERT INTO keyword VALUES (7, 'творчество');

INSERT INTO book_keywords VALUES (1, 1);
INSERT INTO book_keywords VALUES (1, 2);
INSERT INTO book_keywords VALUES (1, 3);
INSERT INTO book_keywords VALUES (2, 4);
INSERT INTO book_keywords VALUES (2, 5);
INSERT INTO book_keywords VALUES (2, 6);
INSERT INTO book_keywords VALUES (2, 7);

--------------------------------------------

INSERT INTO user VALUES (1, 'Саша', 'my@example.com', 'password');
INSERT INTO user VALUES (2, 'John Doe', 'johndoe@gmail.com', 'password');

INSERT INTO role VALUES (1, 'user');
INSERT INTO role VALUES (2, 'admin');

INSERT INTO user_roles VALUES (1, 1);
INSERT INTO user_roles VALUES (2, 2);
