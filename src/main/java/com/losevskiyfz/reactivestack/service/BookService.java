package com.losevskiyfz.reactivestack.service;

import com.losevskiyfz.reactivestack.domain.Book;
import com.losevskiyfz.reactivestack.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Page<Book>> getPaginated(Pageable pageable) {
        return bookRepository.findAllBy(pageable)
                .collectList()
                .zipWith(bookRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    public Mono<Void> delete(String bookId) {
        return bookRepository.deleteById(bookId);
    }

    public Mono<Book> findById(String id) {
        return bookRepository.findById(id);
    }

}
