package com.knf.dev.librarymanagementsystem.dto;

public class AuthorDTO {
    private String name;

    public AuthorDTO(String initName) {
        name = initName;
    }

    public String getName(){
        return name;
    }
}
