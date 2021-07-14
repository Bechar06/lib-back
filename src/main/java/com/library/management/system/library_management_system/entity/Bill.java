package com.library.management.system.library_management_system.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(	name = "bill",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "codeBill")
        })
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer billId;
    @NotNull
    private String codeBill;
    private LocalDateTime date;
    @JoinColumn(name = "memberRecordId", referencedColumnName = "memberRecordId")
    @ManyToOne
    private MemberRecord memberRecordId;
    private Integer amount;
}
