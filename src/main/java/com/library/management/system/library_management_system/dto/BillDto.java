package com.library.management.system.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    private Integer billId;
    private String codeBill;
    private LocalDateTime date;
    private Integer memberId;
    private String memberCode;
    private Integer amount;
}
