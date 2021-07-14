package com.library.management.system.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianDto {

    private Integer librarianId;
    private String name;
    private String login;
    private String password;

}
