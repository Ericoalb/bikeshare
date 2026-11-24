package br.ufal.ic.p2.bikeshare.models;

public class PenalidadeUsoIndevido extends Penalidade {
    private static final long serialVersionUID = 1L;

    public PenalidadeUsoIndevido(double valor, String motivo) {
        super("usoIndevido", valor, motivo);
    }
}
