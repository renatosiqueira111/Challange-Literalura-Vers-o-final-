package com.example.ChallengeLiteraAlura.ONE;

import com.example.ChallengeLiteraAlura.ONE.modelos.DadosAutor;
import com.example.ChallengeLiteraAlura.ONE.modelos.DadosLivro;
import com.example.ChallengeLiteraAlura.ONE.modelos.TodosOsDados;
import com.example.ChallengeLiteraAlura.ONE.repository.DadosRepository;
import com.example.ChallengeLiteraAlura.ONE.repository.DadosRepositoryAutor;
import com.example.ChallengeLiteraAlura.ONE.servicos.ConsumoAPI;
import com.example.ChallengeLiteraAlura.ONE.servicos.ConverteDados;
import com.example.ChallengeLiteraAlura.ONE.servicos.Mensagem;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    String urlBase = "https://gutendex.com/books/?search=";
    List<DadosLivro> livros = new ArrayList<>();
    Scanner leitura = new Scanner(System.in);
    int opcaoDigitada;
    String opcaoNovaConsulta;

    boolean loopMenu = true;


    @Autowired
    private DadosRepository repositorio;
    @Autowired
    private DadosRepositoryAutor dadosAutorRepository;

    public Principal(DadosRepository repositorio,DadosRepositoryAutor dadosAutorRepository) {

        this.repositorio = repositorio;
        this.dadosAutorRepository = dadosAutorRepository;
    }


    public void Iniciar() throws JsonProcessingException {

        while (loopMenu) {
            Mensagem.menuInicial();
            opcaoDigitada = leitura.nextInt();
            leitura.nextLine();
            switch (opcaoDigitada) {
                case 0:
                    loopMenu = false;
                    System.out.println("Aplicação encerrada");
                    break;
                case 1:
                    buscaPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmDeterminadoAno();
                case 5:
                    listarLivroPorIdioma();
            }

        }


    }

    private void listarLivroPorIdioma() {
        System.out.println("Digite en para livros em inglês, pt/português, fr/francês");
            System.out.println(" Digite o idioma para a busca: ");
            String idiomaBusca = leitura.nextLine().trim().toLowerCase();

            List<DadosLivro> livros = repositorio.findAll();
            boolean encontrou = false;

            for (DadosLivro livro : livros) {
                List<String> idiomas = livro.getIdioma();
                for (String idioma : idiomas) {
                    if (idioma.toLowerCase().contains(idiomaBusca)) {
                        System.out.println("Título: " + livro.getTitulo());
                        List<DadosAutor> autores = livro.getAuthors();
                        for (DadosAutor autor : autores) {
                            System.out.println("Autor: " + autor.getNome());
                        }
                        System.out.println("Idioma: " + livro.getIdioma().toString().replace("[", "").replace("]", ""));
                        System.out.println("Downloads: " + livro.getDownloads());
                        System.out.println(); // Linha em branco para separar os livros
                        encontrou = true;
                        return; // Saia do método após encontrar o primeiro livro
                    }
                }


            if (!encontrou) {
                System.out.println("Nenhum livro encontrado no idioma: " + idiomaBusca);
            }
        }

    }

    private void listarAutoresVivosEmDeterminadoAno() {
        List<DadosAutor> autores = dadosAutorRepository.findAll();
        System.out.println("Digite o ano para a busca: ");
        int anoBusca = leitura.nextInt();
        leitura.nextLine(); // Consumir a nova linha

        System.out.println("Autores vivos no ano " + anoBusca + ":");

        boolean encontrou = false;

        for (DadosAutor autor : autores) {
            try {
                // Verificar se os anos de nascimento e morte são válidos
                int anoNascimento = isValidYear(autor.getNascimento()) ? Integer.parseInt(autor.getNascimento()) : Integer.MIN_VALUE;
                Integer anoMorte = isValidYear(autor.getAnoMorte()) ? Integer.parseInt(autor.getAnoMorte()) : null;

                if (anoNascimento != Integer.MIN_VALUE && anoNascimento <= anoBusca && (anoMorte == null || anoMorte > anoBusca)) {
                    System.out.println("Autor: " + autor.getNome());
                    System.out.println("Ano de nascimento: " + anoNascimento);
                    System.out.println("Ano de falecimento: " + (anoMorte != null ? anoMorte : "Ainda vivo"));
                    System.out.println(); // Linha em branco para separar os autores
                    encontrou = true;
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter data para autor: " + autor.getNome());
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum autor encontrado vivo no ano " + anoBusca + ".");
        }
    }

    private boolean isValidYear(String year) {
        if (year == null || year.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(year);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }




    private void listarAutoresRegistrados() {
        List<DadosAutor> autores = dadosAutorRepository.findAll();

        for (DadosAutor autor : autores) {
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Ano de nascimento: " + autor.getNascimento());
            System.out.println("Ano de falecimento: " + (autor.getAnoMorte() != null ? autor.getAnoMorte() : "N/A"));

            if (autor.getLivro() != null) {
                System.out.println("Livros: " + autor.getLivro().getTitulo());
            } else {
                System.out.println("Livros: N/A");
            }

            System.out.println(); // Linha em branco para separar os autores
        }
    }


    private void listarLivrosRegistrados() {
        List<DadosLivro> livros = repositorio.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            System.out.println("Livros registrados:");
            for (DadosLivro livro : livros) {
                System.out.println("Título: " + livro.getTitulo());
                List<DadosAutor> autores = livro.getAuthors();
                for (DadosAutor autor : autores){
                    System.out.println("Autor: " + autor.getNome());
                }
                System.out.println("Idioma: " + livro.getIdioma().toString().replace("[", "").replace("]", ""));
                System.out.println("Downloads: " + livro.getDownloads());


                System.out.println(); // Linha em branco para separar os livros
            }
        }
    }

    private void buscaPorTitulo() throws JsonProcessingException {
        Mensagem.buscaPorTitulo();
        String complementoUrl = leitura.nextLine().replace(" ", "%20").trim();
        String urlCompleta = urlBase + complementoUrl;
        ConsumoAPI consumo = new ConsumoAPI();
        var respostaAPI = consumo.obterDados(urlCompleta);
        ConverteDados conversor = new ConverteDados();
        TodosOsDados dados = conversor.obterDados(respostaAPI, TodosOsDados.class);

        for (DadosLivro livro : dados.getResults()) {
            System.out.println("----- LIVRO -----" + "\n" + "\n" + "Título: " + livro.getTitulo());

            DadosLivro dbLivro = repositorio.saveAndFlush(livro);

            for (DadosAutor autor : livro.getAuthors()) {
                System.out.println("Autor:" + autor.getNome());
                autor.setLivro(dbLivro);
                dadosAutorRepository.save(autor);
            }
            System.out.println("Idioma: " + livro.getIdioma().toString().replace("[", "").replace("]", ""));
            System.out.println("Downloads: " + livro.getDownloads() + "\n" + "\n" + "---------------");

        }




    }
}
