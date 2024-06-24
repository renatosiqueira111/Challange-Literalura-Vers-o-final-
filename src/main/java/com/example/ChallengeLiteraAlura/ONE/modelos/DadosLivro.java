package com.example.ChallengeLiteraAlura.ONE.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "DadosDoLivro")
@JsonIgnoreProperties (ignoreUnknown = true)
public class DadosLivro{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonAlias("title")
    private String titulo;

    @JsonAlias("download_count")
    private Integer downloads;

    @JsonAlias("languages")
    private List<String> idioma;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DadosAutor> authors;
    public List<DadosAutor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<DadosAutor> authors) {
        this.authors = authors;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


