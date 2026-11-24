package br.ufal.ic.p2.bikeshare.exceptions;

public class RazaoSocialNaoPodeSerNulaException extends BikeshareException {
    public RazaoSocialNaoPodeSerNulaException() {
        super("Razao social nao pode ser nula.");
    }
}
