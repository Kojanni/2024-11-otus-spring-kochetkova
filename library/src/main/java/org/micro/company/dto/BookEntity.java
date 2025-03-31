package org.micro.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    private Long id;
    private String title;
    private AuthorEntity author;
    private GenreEntity genre;


    @Override
    public String toString() {
        return id +
                ". Наименование: " + title +
                ". Автор: " + author.getName() + " " + author.getMiddleName() + " " + author.getSurname()  +
                ". Жанр: " + genre.getName();
    }
}
