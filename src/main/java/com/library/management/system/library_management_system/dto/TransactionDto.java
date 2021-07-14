package com.library.management.system.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Integer transId;
    private String codeTrans;
    private Integer memberId;
    private String memberCode;
    private Integer idBook;
    private String bookCode;
    private LocalDateTime dateOfIssue;
    private LocalDateTime dueDate;
}
