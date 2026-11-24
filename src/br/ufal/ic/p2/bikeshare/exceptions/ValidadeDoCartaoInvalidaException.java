package br.ufal.ic.p2.bikeshare.exceptions;

public class ValidadeDoCartaoInvalidaException extends BikeshareException {
    public ValidadeDoCartaoInvalidaException() {
        super("Validade do cartao invalida.");
    }
}
