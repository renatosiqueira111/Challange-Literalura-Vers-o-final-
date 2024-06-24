package com.example.ChallengeLiteraAlura.ONE;

import com.example.ChallengeLiteraAlura.ONE.modelos.DadosAutor;
import com.example.ChallengeLiteraAlura.ONE.repository.DadosRepository;
import com.example.ChallengeLiteraAlura.ONE.repository.DadosRepositoryAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraAluraOneApplication implements CommandLineRunner {

	@Autowired
	private DadosRepository repositorio;
	@Autowired
	private DadosRepositoryAutor dadosAutorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraAluraOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio, dadosAutorRepository);
		principal.Iniciar();
	}
}
