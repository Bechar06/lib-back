package com.library.management.system.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(	name = "transaction",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "codeTrans")
        })
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer transId;
    @NotNull
    private String codeTrans;
    @JoinColumn(name = "memberRecordId", referencedColumnName = "memberRecordId")
    @ManyToOne
    private MemberRecord memberRecordId;
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    @ManyToOne
    private Book bookId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime dateOfIssue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime dueDate;

    private boolean payed;
}
