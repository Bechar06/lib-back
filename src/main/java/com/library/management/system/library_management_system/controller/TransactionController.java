package com.library.management.system.library_management_system.controller;

import com.google.zxing.WriterException;
import com.library.management.system.library_management_system.dto.TransactionDto;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/transaction")
@CrossOrigin(origins = "*", maxAge = 3600)

public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @ApiOperation(value = "View the first Transaction in the database",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Transaction"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(path = "/first", produces = MediaType.APPLICATION_JSON_VALUE)
    private TransactionDto findFirst() {
        TransactionDto transactionDto = transactionService.findFirst();
        if (transactionDto != null)
            return transactionDto;
        else
            return new TransactionDto();
    }

    @ApiOperation(value = "Add a Transaction")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private TransactionDto add(@RequestBody TransactionDto transactionDto) throws LMSException, IOException, WriterException {
        return transactionService.add(transactionDto);
    }

    @ApiOperation(value = "Delete a Transaction")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private void delete(@PathVariable Integer id) throws LMSException, IOException {
        transactionService.delete(id);
    }

    @ApiOperation(value = "Update a Transaction")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private TransactionDto update(@RequestBody TransactionDto TransactionDto) throws IOException, LMSException, WriterException {
        return transactionService.update(TransactionDto);
    }

    @ApiOperation(value = "View a list of available products",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private List<TransactionDto> findAll() {
        return transactionService.findAll();
    }
}
