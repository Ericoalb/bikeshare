package br.ufal.ic.p2.bikeshare.models;

public class PenalidadeAtraso extends Penalidade {
    private static final long serialVersionUID = 1L;

    public static final double VALOR_PADRAO = 10.0;

    public PenalidadeAtraso() {
        super("atraso", VALOR_PADRAO, "Aluguel expirado (> 24h)");
    }

    public PenalidadeAtraso(double valor, String motivo) {
        super("atraso", valor, motivo);
    }
}
