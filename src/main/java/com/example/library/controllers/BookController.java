package com.example.library.controllers;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookResponse;
import com.example.library.services.IBookService;
import com.example.library.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("/api/v1/")
public class BookController {

    private final IBookService bookService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/books")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        BookDto response = bookService.addBook(bookDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public ResponseEntity<BookResponse> getAllBooks(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        BookResponse bookResponse = bookService.getAllBook(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        bookService.updateBook(id, bookDto);

        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


    @GetMapping("/users/books/{bookId}")
    public ResponseEntity<String> reserveBook(@PathVariable long bookId) {
        bookService.reserveBook(bookId);

        return new ResponseEntity<>("Reserved!", HttpStatus.OK);
    }

    @GetMapping("/users/books/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable long bookId) {
        bookService.returnBook(bookId);

        return new ResponseEntity<>("Returned!", HttpStatus.OK);
    }


}
