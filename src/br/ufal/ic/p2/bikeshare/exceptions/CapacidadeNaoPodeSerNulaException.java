package br.ufal.ic.p2.bikeshare.exceptions;

public class CapacidadeNaoPodeSerNulaException extends BikeshareException {
    public CapacidadeNaoPodeSerNulaException() {
        super("Capacidade nao pode ser nula.");
    }
}
