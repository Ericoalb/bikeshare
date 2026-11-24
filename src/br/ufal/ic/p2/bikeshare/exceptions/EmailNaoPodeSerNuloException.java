package br.ufal.ic.p2.bikeshare.exceptions;

public class EmailNaoPodeSerNuloException extends BikeshareException {
    public EmailNaoPodeSerNuloException() {
        super("Email nao pode ser nulo.");
    }
}
