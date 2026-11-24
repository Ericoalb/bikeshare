package br.ufal.ic.p2.bikeshare.exceptions;

public class FranquiaNaoPodeSerNulaException extends BikeshareException {
    public FranquiaNaoPodeSerNulaException() {
        super("Franquia nao pode ser nula.");
    }
}
