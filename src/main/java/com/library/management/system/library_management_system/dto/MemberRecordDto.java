package com.library.management.system.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRecordDto {
    private Integer memberRecordId;
    private String codeMemberRecord;
    private String type;
    private LocalDateTime dateOfMemberRecordship;
    private Integer noBookIssued;
    private Integer maxBookLimit;
    private String fullName;
    private String adress;
    private String phoneNo;
}
