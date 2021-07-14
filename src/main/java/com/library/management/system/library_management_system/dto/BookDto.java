package com.library.management.system.library_management_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Integer bookId;
    private String codeBook;
    private String author;
    private String title;
    private Double price;
    private Integer rackNo;
    private String status;
    private String edition;
    private LocalDateTime dateOfPurchase;
}
