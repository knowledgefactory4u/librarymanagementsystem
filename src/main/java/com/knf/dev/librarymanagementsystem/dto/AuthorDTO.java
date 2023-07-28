package com.knf.dev.librarymanagementsystem.dto;

public class AuthorDTO {
    private String name;

    private Long id;

    public AuthorDTO(String initName) {
        name = initName;
    }

    public String getName(){
        return name;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
