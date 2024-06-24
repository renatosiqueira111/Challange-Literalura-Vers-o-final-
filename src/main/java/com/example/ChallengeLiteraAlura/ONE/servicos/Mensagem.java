package com.example.ChallengeLiteraAlura.ONE.servicos;


public class Mensagem {

    public static void menuInicial(){
        String msgMenu = "\n" +
                "Escolha o número de sua opção: \n" +
                "\n" +
                "1- buscar livro pelo título;\n" +
                "\n" +
                "2- listar livros registrados;\n" +
                "\n" +
                "3- listar autores registrados;\n" +
                "\n" +
                "4- listar autores vivos em um determinado ano;\n" +
                "\n" +
                "5- listar livros em um determinado idioma;\n" +
                "\n" +
                "0- sair.";
        System.out.println(msgMenu);
    }

    public static void buscaPorTitulo(){
        System.out.println("Digite o título do livro: ");

    }

    public static void novaConsulta(){
        System.out.println("Deseja fazer uma nova ação? s/n");
    }
}
