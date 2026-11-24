package br.ufal.ic.p2.bikeshare.models;

public class PenalidadeDano extends Penalidade {
    private static final long serialVersionUID = 1L;

    public PenalidadeDano(double valor, String motivo) {
        super("dano", valor, motivo);
    }
}
