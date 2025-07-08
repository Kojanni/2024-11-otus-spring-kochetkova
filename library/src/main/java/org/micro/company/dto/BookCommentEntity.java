package org.micro.company.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_comment")
public class BookCommentEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Column(name = "comment")
    @Lob
    private String comment;
}
