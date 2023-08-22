package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

//@Data
//@SuperBuilder
//@NoArgsConstructor
//@JsonInclude(NON_DEFAULT)
public class Image {

    private Long id;
    private String name;
    private String type;
    private byte[] img;

    public Image(String name, String type, byte[] img) {
        this.name = name;
        this.type = type;
        this.img = img;
    }
}
