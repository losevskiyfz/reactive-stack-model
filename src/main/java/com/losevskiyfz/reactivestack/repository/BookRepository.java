package com.losevskiyfz.reactivestack.repository;

import com.losevskiyfz.reactivestack.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book,String> {
    Flux<Book> findAllBy(Pageable pageable);
}
