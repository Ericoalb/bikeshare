package br.ufal.ic.p2.bikeshare.exceptions;

public class ValidadeDoCartaoNaoPodeSerNulaException extends BikeshareException {
    public ValidadeDoCartaoNaoPodeSerNulaException() {
        super("Validade do cartao nao pode ser nula.");
    }
}
