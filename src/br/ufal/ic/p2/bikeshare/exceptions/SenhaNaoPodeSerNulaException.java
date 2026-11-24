package br.ufal.ic.p2.bikeshare.exceptions;

public class SenhaNaoPodeSerNulaException extends BikeshareException {
    public SenhaNaoPodeSerNulaException() {
        super("Senha nao pode ser nula.");
    }
}
