package com.library.management.system.library_management_system.controller;

import com.google.zxing.WriterException;
import com.library.management.system.library_management_system.dto.BookDto;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.repository.BookRepository;
import com.library.management.system.library_management_system.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/book")
public class BookController {


    @Autowired
    BookService bookService;

    @Autowired
    SimpMessageSendingOperations messagingTemplate;

    @Autowired
    BookRepository bookRepository;


    @ApiOperation(value = "View the first Book on the database",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Book"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping("/first")
    public BookDto findFirst() {
        BookDto bookDto = bookService.findFirst();
        if (bookDto != null)
            return bookDto;
        else
            return new BookDto();
    }

    @ApiOperation(value = "Send book detail to the web application using web sockets",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully sent book detail to the web application using web sockets"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping("barCode/{codeBook}")
    public void barCode(@PathVariable String codeBook) {
        messagingTemplate.convertAndSend("/topic/progress", "UploadBook");
    }


    @ApiOperation(value = "Add a Book")
    @PostMapping()
    public BookDto add(@RequestBody BookDto bookDto) throws LMSException, IOException, WriterException {
        return bookService.add(bookDto);
    }


    @ApiOperation(value = "Delete a Book")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws LMSException, IOException {
        bookService.delete(id);
    }


    @ApiOperation(value = "Update a Book")
    @PutMapping()
    public BookDto update(@RequestBody BookDto bookDto) throws IOException, LMSException, WriterException {
        return bookService.update(bookDto);
    }

    @ApiOperation(value = "View a list of available Books",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping()
    public List<BookDto> findByAll() {
        return bookService.findAll();
    }


}
