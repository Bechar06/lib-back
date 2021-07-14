package com.library.management.system.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(	name = "book",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "codeBook")
        })
@Data
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;
    @NotNull()
    private String codeBook;
    private String author;
    private String title;
    private Double price;
    private Integer rackNo;
    private String status;
    private String edition;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime dateOfPurchase;

}
