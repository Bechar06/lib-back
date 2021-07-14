package com.library.management.system.library_management_system.converter;

import com.library.management.system.library_management_system.dto.BookDto;
import com.library.management.system.library_management_system.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter extends Converter<Book, BookDto> {
}
