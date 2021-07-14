package com.library.management.system.library_management_system.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(	name = "librarian",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "librarianCode")
        })
public class Librarian implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer librarianId;
    @NotNull
    private String librarianCode;
    private String name;
    private String login;
    private String password;
}
