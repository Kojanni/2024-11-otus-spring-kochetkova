drop table if exists book;
create table book (
    id      bigserial not null PRIMARY KEY,
    title   varchar(255) not null ,
    genreId bigint not null ,
    authorId bigint not null
);

drop table if exists genre;
create table genre (
    id       bigserial not null  PRIMARY KEY,
    name  varchar(255) unique not null
);

drop table if exists author;
create table author (
    id       bigserial not null  PRIMARY KEY,
    name varchar(50) not null ,
    middleName varchar(50),
    surname varchar(50)
);

alter table book add constraint fk_bookGenre
foreign key (genreId) references genre(Id);

alter table book add constraint fk_bookAuthor
foreign key (authorId) references author(Id);

create index idx_author_unique_name on author (name, middleName, surname);