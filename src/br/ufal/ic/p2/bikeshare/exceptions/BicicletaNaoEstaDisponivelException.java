package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaNaoEstaDisponivelException extends BikeshareException {
    public BicicletaNaoEstaDisponivelException() {
        super("Bicicleta nao esta disponivel.");
    }
}
