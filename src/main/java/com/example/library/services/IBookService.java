package com.example.library.services;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookResponse;
import com.example.library.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    BookDto addBook(BookDto bookDto);

    BookResponse getAllBook(int pageNo, int pageSize, String sortBy, String sortDir);

    void updateBook(long id, BookDto bookDto);

    void deleteBook(long id);

    void reserveBook(long bookId);

    void returnBook(long bookId);
}
