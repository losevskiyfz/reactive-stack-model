package com.losevskiyfz.reactivestack.mapper;

import com.losevskiyfz.reactivestack.domain.Book;
import com.losevskiyfz.reactivestack.domain.PostBookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    Book toBook(PostBookDto dto);
    PostBookDto toPostBookDto(Book bookRecord);
}
