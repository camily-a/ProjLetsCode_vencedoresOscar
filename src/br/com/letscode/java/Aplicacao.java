package br.com.letscode.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    }

    List<Oscar> lista = new ArrayList<>();

    List<Oscar> listaCompleta = new ArrayList<>();


    private List<Oscar> prepararLeituraArquivoCsv( String path) {
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            this.lista = lines.skip(1)
                    .map(Oscar::fromLine)
                    .collect(Collectors.toList());// Java 11

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.lista;
    }

    private void listaCompleta() {
        if (listaCompleta.isEmpty()) {
            listaCompleta.addAll(prepararLeituraArquivoCsv("files/oscar_age_male.csv"));
            listaCompleta.addAll(prepararLeituraArquivoCsv("files/oscar_age_female.csv"));
        }
    }


    public void atorMaisJovemAGanharOscar() {
        prepararLeituraArquivoCsv("files/oscar_age_male.csv");
        System.out.print("O ator mais jovem a ganhar o Oscar é ");
        this.lista.stream()
                .min(Comparator.comparingInt(Oscar::getIdade))
                .ifPresent(x -> System.out.println(x.getNome() + " que recebeu o prêmio em " + x.getAno() +
                        " aos " + x.getIdade() + " anos de idade , por sua atuação no filme " + x.getFilme() + ".\n"));

    }

    public void atrizMaisPremiada() {
        this.prepararLeituraArquivoCsv("files/oscar_age_female.csv");
        System.out.print("A atriz mais premiada é ");
        Map<String, Long> numeroPremiacoes = this.lista.stream()
                .map(Oscar::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        numeroPremiacoes.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(x -> System.out.println(x.getKey() + " com um total de " + x.getValue() + " premiações. \n"));
    }

    public void atrizEntre20e30maisPremiada() {
        this.prepararLeituraArquivoCsv("files/oscar_age_female.csv");
        System.out.print("A atriz com mais premiações entre 20 e 30 anos é ");
        Map<String, Long> numeroPremiacoes = this.lista.stream()
                .filter( atriz -> atriz.getIdade() >= 20 && atriz.getIdade() <= 30)
                .map(Oscar::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        numeroPremiacoes.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(x -> System.out.println(x.getKey() + " com um total de " + x.getValue() + " premiações. \n "));
    }

    public void atoresEAtrizesQueReceberamMaisDeUmOscar() {
        listaCompleta();
        System.out.print("Os atores ou atrizes que receberam mais de um Oscar são \n");
        var numeroPremiacoes = this.listaCompleta.stream()
                .map(Oscar::getNome)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        var NomesQtdPremiacoes = numeroPremiacoes.entrySet().stream()
                .filter(x -> x.getValue() >= 2)
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());

        NomesQtdPremiacoes.forEach(x -> System.out.println(x.getKey() + " = " + x.getValue() + " Oscars."));
        System.out.println("\n");
    }

    public void buscaAtorAtriz(String nome) {
        listaCompleta();
        var resBusca = listaCompleta.stream()
        .filter( linha -> linha.getNome().equals(nome))
        .sorted(Comparator.comparingInt(Oscar::getAno))
        .collect(Collectors.toList());

        System.out.print(nome.toUpperCase(Locale.ROOT) + " foi premiado(a) " + resBusca.size() + " vezes : \n");
        resBusca.forEach(x -> System.out.println("Em " + x.getAno() +
                ", aos " + x.getIdade() + " anos de idade por sua atuação no filme '" + x.getFilme()+ "'"));
        System.out.print("\n");

        // optional??

    }






}
