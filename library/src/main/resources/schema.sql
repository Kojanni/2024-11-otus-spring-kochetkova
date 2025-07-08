DROP TABLE IF EXISTS book_comment;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;

create table book (
    id      bigserial not null PRIMARY KEY,
    title   varchar(255) not null ,
    genre_id bigint not null ,
    author_id bigint not null
);

create table genre (
    id       bigserial not null  PRIMARY KEY,
    name  varchar(255) unique not null
);

create table author (
    id       bigserial not null  PRIMARY KEY,
    name varchar(50) not null ,
    middle_name varchar(50),
    surname varchar(50)
);

create table book_comment
(
    id      bigserial primary key not null,
    author_id bigint references author not null,
    book_id bigint references book not null,
    comment text
);

create index fkx_book_comment_author_id on book_comment (author_id);
create index fkx_book_comment_book_id on book_comment (book_id);

alter table book add constraint fk_bookGenre
foreign key (genre_id) references genre(Id);

alter table book add constraint fk_bookAuthor
foreign key (author_id) references author(Id);

create index idx_author_unique_name on author (name, middle_name, surname);