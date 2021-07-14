package com.library.management.system.library_management_system.service;

import com.google.zxing.WriterException;
import com.library.management.system.library_management_system.converter.BookConverter;
import com.library.management.system.library_management_system.dto.BookDto;
import com.library.management.system.library_management_system.entity.Book;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.model.QRCodeGenerator;
import com.library.management.system.library_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookConverter bookConverter;

    @Autowired
    QRCodeGenerator qrCodeGenerator;


    public BookDto findFirst() {
        Book book = bookRepository.findFirstByOrderByBookId();
        if (book == null) {
            return null;
        } else {
            return bookConverter.convert(book);
        }
    }

    public BookDto findByCodeBook(String Code) {
        Book book = bookRepository.findByCodeBook(Code);
        if (book == null) {
            return null;
        } else {
            return bookConverter.convert(book);
        }
    }


    public BookDto add(BookDto bookDto) throws LMSException, IOException, WriterException {
        if (bookRepository.existsByCodeBook(bookDto.getCodeBook())) {
            throw new LMSException("Ce code \"code Livre\" existe déjà");
        }
        BookDto bookDto1 = bookConverter.convert(bookRepository.save(bookConverter.convert(bookDto)));
        qrCodeGenerator.generateQRCodeImage(bookDto1.getCodeBook(), 350, 350);
        return bookDto1;
    }

    public void delete(Integer id) throws LMSException, IOException {
        Optional<Book> book = bookRepository.findById(id);
        try {
            if (book.isPresent()) {
                bookRepository.deleteById(id);
                Files.deleteIfExists(Paths.get(QRCodeGenerator.QR_CODE_IMAGE_PATH + book.get().getCodeBook() + ".png"));
            } else {
                throw new LMSException("Ce livre ne plus existé");
            }
        }  catch (Exception e) {
            throw new LMSException("Ce livre est déjà loué par un client.\n pour supprimer ce livre, la transaction doit être terminée");
        }
    }

    public BookDto update(BookDto bookDto) throws IOException, LMSException, WriterException {
        Optional<Book> book = bookRepository.findById(bookDto.getBookId());
        if (!book.get().getCodeBook().equals(bookDto.getCodeBook()) && bookRepository.existsByCodeBook(bookDto.getCodeBook())) {
            throw new LMSException("Ce code \"code Livre\" existe déjà");
        }
        String oldBookCode = book.get().getCodeBook();
        BookDto bookToReturn = bookConverter.convert(bookRepository.save(bookConverter.convert(bookDto)));
        Files.deleteIfExists(Paths.get(QRCodeGenerator.QR_CODE_IMAGE_PATH + oldBookCode + ".png"));
        qrCodeGenerator.generateQRCodeImage(bookToReturn.getCodeBook(), 350, 350);
        return bookToReturn;
    }

    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> booksDto = new ArrayList<>();
        books.forEach(item -> {
            booksDto.add(bookConverter.convert(item));
        });
        return booksDto;
    }

}
