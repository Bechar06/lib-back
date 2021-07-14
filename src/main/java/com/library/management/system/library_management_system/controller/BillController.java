package com.library.management.system.library_management_system.controller;

import com.library.management.system.library_management_system.dto.BillDto;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.service.BillService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/bill")
public class BillController {

    @Autowired
    private BillService billService;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @ApiOperation(value = "Add a bill")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private BillDto add(@RequestBody BillDto billDto) throws LMSException {
        return billService.add(billDto);
    }

    @ApiOperation(value = "Delete a bill")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws LMSException, IOException {
        billService.delete(id);
    }

    @ApiOperation(value = "Update a bill")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private BillDto update(@RequestBody BillDto billDto) throws LMSException {
        return billService.update(billDto);
    }

    @ApiOperation(value = "View a list of available bills",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping()
    private List<BillDto> findAll() {
        return billService.findAll();
    }
    @ApiOperation(value = "View the first bill on the database",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved bill"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping("/first")
    public BillDto findFirst() {
        BillDto billDto = billService.findFirst();
        if (billDto != null)
            return billDto;
        else
            return new BillDto();
    }
    @ApiOperation(value = "Print a bill",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully generated file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping("/print/{billId}")
    public ResponseEntity<byte[]> report(@PathVariable Integer billId) {
        byte[] bytes = billService.getBytes(billId);
        String currentDate = LocalDateTime.now().format(formatter);
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename("Facture_"+currentDate + ".pdf").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf; charset=UTF-8")
                .headers(headers)
                .body(bytes);
    }


}
