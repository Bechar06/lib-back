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
    private Double amount;
    private Integer bookId;
    
    public static BillDto map(TransactionDto transactionDto, Double bookPrice) {
    	BillDto bill = new BillDto();
    	bill.setBookId(transactionDto.getIdBook());
    	bill.setCodeBill(transactionDto.getCodeTrans());
    	bill.setMemberId(transactionDto.getMemberId());
    	bill.setMemberCode(transactionDto.getMemberCode());
    	bill.setDate(LocalDateTime.now());
    	bill.setAmount(bookPrice);
    	return bill;
    }
}
