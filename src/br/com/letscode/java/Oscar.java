package br.com.letscode.java;

import static java.lang.Integer.parseInt;

public class Oscar {

    private int ano ;
    private int idade;
    private String nome;
    private String filme;

    public Oscar(int ano, int idade, String nome, String filme) {
        this.ano = ano;
        this.idade = idade;
        this.nome = nome;
        this.filme = filme;
    }

    public int getAno() {
        return ano;
    }

    public String getFilme() {
        return filme;
    }

    public int getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public static Oscar fromLine(String line) {
        String[] split = line.split("; ");
        return new Oscar(
                parseInt(split[1]),
                parseInt(split[2]),
                split[3],
                split[4]
        );
    }

    @Override
    public String toString() {
        return "Oscar{" +
                "ano=" + ano +
                ", idade=" + idade +
                ", nome='" + nome + '\'' +
                ", filme='" + filme + '\'' +
                '}';
    }
}
