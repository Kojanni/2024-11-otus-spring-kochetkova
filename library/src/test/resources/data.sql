insert into genre
    ( name)
values
    ( 'Военный роман'),
    ( 'Комедия');

insert into author
    (name, middle_name, surname)
values
    ('Константин', 'Михайлович', 'Симонов'),
    ( 'Антон', 'Павлович', 'Чехов');

insert into book
    (title, genre_id, author_id)
values
    ('Живые и мёртвые', 1, 1),
    ( 'Вишнёвый сад', 2, 2);

insert into book_comment
    (author_id, book_id, comment)
values
    (1, 1, 'Cool'),
    (2, 2, 'Just buy'),
    (1, 2, 'Who want to buy?');