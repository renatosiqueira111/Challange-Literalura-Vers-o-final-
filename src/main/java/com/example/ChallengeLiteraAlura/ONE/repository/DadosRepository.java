package com.example.ChallengeLiteraAlura.ONE.repository;

import com.example.ChallengeLiteraAlura.ONE.modelos.DadosLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DadosRepository extends JpaRepository<DadosLivro, Long> {

}
