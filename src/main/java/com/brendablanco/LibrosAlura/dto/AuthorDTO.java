package com.brendablanco.LibrosAlura.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDTO {
    private String name;
    private Integer birth_year;
    private Integer death_year;
}
