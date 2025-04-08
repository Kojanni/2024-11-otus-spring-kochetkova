package org.micro.company.controller;

import lombok.RequiredArgsConstructor;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.micro.company.dto.GenreEntity;
import org.micro.company.service.AuthorService;
import org.micro.company.service.BookService;
import org.micro.company.service.GenreService;
import org.micro.company.service.IOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ShellComponent
public class ShellController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final IOService ioService;

    @ShellMethod(key = {"book list", "all", "b"}, value = "Show all books")
    public void getBookList() {
        List<BookEntity> books = bookService.findAll();
        books.forEach(book -> ioService.write(book.toString()));
    }

    @ShellMethod(key = {"save book", "save b"}, value = "Save book to library")
    public void saveBook() {
        ioService.write("Введите наименование книги");
        String title = ioService.read();
        ioService.write("Введите жанр");
        String genreName = ioService.read();
        ioService.write("Введите ФИО автора");
        String authorFullName = ioService.read();

        GenreEntity genre = genreService.findByName(genreName);
        AuthorEntity author = authorService.findByFullName(authorFullName);

        Optional.ofNullable(bookService.saveBook(title, author, genre)).ifPresentOrElse(
                book -> ioService.write(book.toString()),
                () -> ioService.write("Book \"" + title + "\" not found")
        );
    }

    @ShellMethod(key = {"findBookById", "f id b"}, value = "Find book by Id")
    public void findBookById() {
        long id = ioService.readInt();
        Optional.ofNullable(bookService.findById(id)).ifPresentOrElse(
                book -> ioService.write(book.toString()),
                () -> ioService.write("Book with id " + id + " not found")
        );
    }

    @ShellMethod(key = {"findBookByAuthor", "f author b"}, value = "Find books by author")
    public void findBooksByAuthor() {
        ioService.write("Введите ФИО автора");
        String authorFullName = ioService.read();

        AuthorEntity author = authorService.findByFullName(authorFullName);

        Optional.ofNullable(bookService.findByAuthor(author)).ifPresentOrElse(
                book -> ioService.write(book.toString()),
                () -> ioService.write("Book with author " + authorFullName + " not found")
        );
    }

    @ShellMethod(key = {"deleteBookById", "d id b"}, value = "Delete book by Id")
    public void deleteBookById() {
        long id = ioService.readInt();
        bookService.deleteById(id);
        ioService.write("Book with id " + id + " deleted.");
    }

    @ShellMethod(key = "count b", value = "Count of all books")
    public void countBook() {
        ioService.write(String.valueOf(bookService.count()));
    }

    @ShellMethod(key = {"genre list", "g"}, value = "Show all genres")
    public void getGenreList() {
        List<GenreEntity> genres = genreService.findAll();
        genres.forEach(genre -> ioService.write(genre.toString()));
    }

    @ShellMethod(key = {"save genre", "save g"}, value = "Save genre")
    public void saveGenre() {
        ioService.write("Введите жанр");
        String genreName = ioService.read();

        Optional.ofNullable(genreService.save(genreName)).ifPresentOrElse(
                genre -> ioService.write(genre.toString()),
                () -> ioService.write("Genre: " + genreName + " not save")
        );
    }

    @ShellMethod(key = {"author list", "a"}, value = "Show all authors")
    public void getAuthorList() {
        List<AuthorEntity> authors = authorService.findAll();
        authors.forEach(author -> ioService.write(author.toString()));
    }

    @ShellMethod(key = {"save author", "save a"}, value = "Save author")
    public void saveAuthor() {
        ioService.write("Введите фамилию автора");
        String surname = ioService.read();

        ioService.write("Введите имя автора");
        String name = ioService.read();

        ioService.write("Введите отчество автора");
        String middleName = ioService.read();

        Optional.ofNullable(authorService.save(surname, name, middleName)).ifPresentOrElse(
                author -> ioService.write(author.toString()),
                () -> ioService.write("Author: " + surname + " " + name + " " + middleName + " not save")
        );
    }

}
