package com.example.library.services.impl;

import com.example.library.converter.Mapper;
import com.example.library.dto.BookDto;
import com.example.library.dto.BookResponse;
import com.example.library.entities.Book;
import com.example.library.entities.UserEntity;
import com.example.library.exception.LibraryAPIException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repositories.IBookRepository;
import com.example.library.repositories.IUserRepository;
import com.example.library.services.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImp implements IBookService {

    private final IBookRepository bookRepository;
    private final Mapper mapper;
    private final IUserRepository userRepository;

    @Override
    public BookDto addBook(BookDto bookDto) {
        if (bookRepository.existsByName(bookDto.getName())) {
            throw new LibraryAPIException(HttpStatus.BAD_REQUEST, "Book is already exists!");
        }

        Book book = mapper.toModel(bookDto, Book.class);
        bookRepository.save(book);
        return bookDto;
    }

    @Override
    public BookResponse getAllBook(int pageNo, int pageSize, String sortBy, String sortDir) {

        // check sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Book> bookPage = bookRepository.findAll(pageable);

        // get content from page object
        List<Book> bookList = bookPage.getContent();
        List<BookDto> content = mapper.toList(bookList, BookDto.class);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(content);
        bookResponse.setPageNo(bookPage.getNumber());
        bookResponse.setPageSize(bookPage.getSize());
        bookResponse.setTotalElements(bookPage.getTotalElements());
        bookResponse.setTotalPages(bookPage.getTotalPages());
        bookResponse.setLast(bookPage.isLast());

        return bookResponse;
    }

    @Override
    public void updateBook(long id, BookDto bookDto) {

        var book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Book not found with id: %d", id)));
        book.setName(bookDto.getName());
        book.setCategory(bookDto.getCategory());
        book.setStock(bookDto.getStock());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void reserveBook(long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Book not found with id: %d", bookId)));
        if (book.getStock() < 1) {
            throw new ResourceNotFoundException("Out of stock");
        }
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
        List<Book> bookList = userEntity.getBooks();
        bookList.add(book);
        userEntity.setBooks(bookList);
        userRepository.save(userEntity);
    }

    @Override
    public void returnBook(long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email not found"));

        if (!isIdExistInBorrowedList(userEntity.getBooks(), bookId)) {
            throw new LibraryAPIException(HttpStatus.BAD_REQUEST, "you didn't borrow this book");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Book not found with id: %d", bookId)));
        List<Book> bookList = userEntity.getBooks();
        bookList.removeIf(returnedBook -> returnedBook.getId() == bookId);
        userEntity.setBooks(bookList);
        userRepository.save(userEntity);
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

    }

    private static Boolean isIdExistInBorrowedList(List<Book> list, long bookId) {
        for (Book book : list) {
            if (book.getId() == bookId) return true;
        }
        return false;
    }
}
