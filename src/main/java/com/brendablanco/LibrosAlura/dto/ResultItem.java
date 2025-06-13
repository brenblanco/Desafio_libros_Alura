package com.brendablanco.LibrosAlura.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResultItem {
    private int id;
    private String title;
    private List<AuthorDTO> authors;
    private List<String> languages;
    private List<String> subjects;
    private double download_count;



}
