insert into genre
    ( name)
values
    ( 'Военный роман'),
    ( 'Комедия');

insert into author
    (name, middleName, surname)
values
    ('Константин', 'Михайлович', 'Симонов'),
    ( 'Антон', 'Павлович', 'Чехов');

insert into book
    (title, genreId, authorId)
values
    ('Живые и мёртвые', 1, 1),
    ( 'Вишнёвый сад', 2, 2);