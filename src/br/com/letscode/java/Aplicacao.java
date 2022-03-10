package br.com.letscode.java;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public class Aplicacao {

    public static void main(String[] args) {

        Aplicacao app = new Aplicacao();
        app.atorMaisJovemAGanharOscar();
        app.atrizMaisPremiada();
        app.atrizEntre20e30maisPremiada();
        app.atoresEAtrizesQueReceberamMaisDeUmOscar();
        app.buscaAtorAtriz("Katharine Hepburn");
        app.buscaAtorAtriz("Tom Hanks");
        app.buscaAtorAtriz("Thomas Heilord");


    }

    public void atorMaisJovemAGanharOscar() {
        LeituraArquivo la = new LeituraArquivo("files/oscar_age_male.csv");
        System.out.print("O ator mais jovem a ganhar o Oscar é ");
        la.getlista().stream()
                .min(Comparator.comparingInt(Oscar::getIdade))
                .ifPresent(x -> System.out.println(x.getNome() + " que recebeu o prêmio em " + x.getAno() +
                        " aos " + x.getIdade() + " anos de idade , por sua atuação no filme " + x.getFilme() + ".\n"));

    }

    public void atrizMaisPremiada() {
        LeituraArquivo la = new LeituraArquivo("files/oscar_age_female.csv");
        System.out.print("A atriz mais premiada é ");
        la.getlista().stream()
                .map(Oscar::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(x -> System.out.println(x.getKey() + " com um total de " + x.getValue() + " premiações. \n"));
    }

    public void atrizEntre20e30maisPremiada() {
        LeituraArquivo la = new LeituraArquivo("files/oscar_age_female.csv");
        System.out.print("A atriz com mais premiações entre 20 e 30 anos é ");
        la.getlista().stream()
                .filter( atriz -> atriz.getIdade() >= 20 && atriz.getIdade() <= 30)
                .map(Oscar::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(x -> System.out.println(x.getKey() + " com um total de " + x.getValue() + " premiações. \n "));
    }

    public void atoresEAtrizesQueReceberamMaisDeUmOscar() {
        LeituraArquivo la = new LeituraArquivo("listaCompleta");
        System.out.print("Os atores ou atrizes que receberam mais de um Oscar são: \n");
        var NomesEqtdPremiacoes = la.getlista().stream()
                .map(Oscar::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(x -> x.getValue() >= 2)
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());

        NomesEqtdPremiacoes.forEach(x -> System.out.println(x.getKey() + " = " + x.getValue() + " Oscars."));
        System.out.println("\n");
    }

    public void buscaAtorAtriz(String nome) {
        LeituraArquivo la = new LeituraArquivo("listaCompleta");
        var resBusca = la.getlista().stream()
                .filter( linha -> linha.getNome().equals(nome))
                .sorted(Comparator.comparingInt(Oscar::getAno))
                .collect(Collectors.toList());

        if (resBusca.isEmpty()) {
            System.out.print("Não foi encontrado nenhum prêmio associado a " + nome.toUpperCase() + ".\n");
        } else {
            System.out.print(nome.toUpperCase() + " foi premiado(a) " + resBusca.size() + " vezes : \n");
            resBusca.forEach(x -> System.out.println("Em " + x.getAno() +
                    ", aos " + x.getIdade() + " anos de idade por sua atuação no filme '" + x.getFilme()+ "'"));
            System.out.print("\n");
        }

    }






}
