package com.example.ChallengeLiteraAlura.ONE.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public class TodosOsDados {
    private List<DadosLivro> results;

    public List<DadosLivro> getResults() {
        return results;
    }

    public void setResults(List<DadosLivro> results) {
        this.results = results;
    }





}
