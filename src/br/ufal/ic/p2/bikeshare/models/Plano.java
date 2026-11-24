package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;

public class Plano implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private int franquiaMinutos;
    private double valorMinutoExcedente;
    private double mensalidade;

    public Plano(String nome, int franquiaMinutos, double valorMinutoExcedente, double mensalidade) {
        this.nome = nome;
        this.franquiaMinutos = franquiaMinutos;
        this.valorMinutoExcedente = valorMinutoExcedente;
        this.mensalidade = mensalidade;
    }

    public String getNome() { return nome; }
    public int getFranquiaMinutos() { return franquiaMinutos; }
    public double getValorMinutoExcedente() { return valorMinutoExcedente; }
    public double getMensalidade() { return mensalidade; }
}
