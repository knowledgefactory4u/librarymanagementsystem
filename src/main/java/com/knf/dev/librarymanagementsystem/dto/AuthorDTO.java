package com.knf.dev.librarymanagementsystem.dto;

import java.beans.JavaBean;

@JavaBean
public class AuthorDTO {
    private String name;

    private String description;

    private Long id;

    public AuthorDTO(String initName, String initDescription) {

        name = initName;
        description = initDescription;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
