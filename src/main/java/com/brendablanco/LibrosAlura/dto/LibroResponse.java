package com.brendablanco.LibrosAlura.dto;

import java.util.List;

public class LibroResponse {
    private List<ResultItem> results;

    public List<ResultItem> getResults() {
        return results;
    }

    public void setResults(List<ResultItem> results) {
        this.results = results;
    }
}
