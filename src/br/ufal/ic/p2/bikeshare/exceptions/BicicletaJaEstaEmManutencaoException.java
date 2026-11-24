package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaJaEstaEmManutencaoException extends BikeshareException {
    public BicicletaJaEstaEmManutencaoException() {
        super("Bicicleta ja esta em manutencao.");
    }
}
