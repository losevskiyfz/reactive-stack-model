package com.losevskiyfz.reactivestack.controller;

import com.losevskiyfz.reactivestack.domain.Book;
import com.losevskiyfz.reactivestack.domain.PostBookDto;
import com.losevskiyfz.reactivestack.mapper.BookMapper;
import com.losevskiyfz.reactivestack.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public Mono<ResponseEntity<Book>> save(@Valid @RequestBody PostBookDto bookRecord) {
        Book bookToSave = bookMapper.toBook(bookRecord);
        return bookService.save(bookToSave)
                .map(savedBook -> ResponseEntity
                            .created(URI.create("/api/v1/book/" + savedBook.id()))
                            .body(savedBook)
                );
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<Void>> update(@PathVariable String id, @Valid @RequestBody PostBookDto bookRecord) {
        return bookService.findById(id)
                .flatMap(_ -> {
                    Book bookToSave = bookMapper.toBook(bookRecord).withId(id);
                    return bookService.save(bookToSave)
                            .then(Mono.just(ResponseEntity.noContent().<Void>build()));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public Mono<ResponseEntity<Page<Book>>> findAllProducts(Pageable pageable) {
        return bookService.getPaginated(pageable)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String id) {
        return bookService.delete(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}