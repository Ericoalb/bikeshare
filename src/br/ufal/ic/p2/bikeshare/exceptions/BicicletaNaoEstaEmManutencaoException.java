package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaNaoEstaEmManutencaoException extends BikeshareException {
    public BicicletaNaoEstaEmManutencaoException() {
        super("Bicicleta nao esta em manutencao.");
    }
}
