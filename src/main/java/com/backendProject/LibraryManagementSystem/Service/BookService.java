package com.backendProject.LibraryManagementSystem.Service;

import com.backendProject.LibraryManagementSystem.DTO.BookRequestDto;
import com.backendProject.LibraryManagementSystem.DTO.BookResponseDto;
import com.backendProject.LibraryManagementSystem.Entity.Author;
import com.backendProject.LibraryManagementSystem.Entity.Book;
import com.backendProject.LibraryManagementSystem.Repository.AuthorRepository;
import com.backendProject.LibraryManagementSystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;
    public BookResponseDto addBook(BookRequestDto bookRequestDto) throws Exception {

        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();
        Book book = new Book();

        book.setTitle(bookRequestDto.getTitle());
        book.setGenre(bookRequestDto.getGenre());
        book.setPrice(bookRequestDto.getPrice());
        book.setIssued(false);
        book.setAuthor(author);

        author.getBooks().add(book);
        authorRepository.save(author);

        //create a response
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setPrice(book.getPrice());

        return bookResponseDto;
    }
}
