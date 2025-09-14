package org.bookstore.bookstore.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.print.DocFlavor;
import java.time.LocalDateTime;

@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private Long productId;
    private Long isbn;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publishedDate;
}
