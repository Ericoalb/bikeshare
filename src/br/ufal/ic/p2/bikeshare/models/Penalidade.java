package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;

public abstract class Penalidade implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String tipo;
    private final double valor;
    private final String motivo;

    public Penalidade(String tipo, double valor, String motivo) {
        this.tipo = tipo;
        this.valor = valor;
        this.motivo = motivo;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getMotivo() {
        return motivo;
    }
}
