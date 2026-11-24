package br.ufal.ic.p2.bikeshare.exceptions;

public class CpfNaoPodeSerNuloException extends BikeshareException {
    public CpfNaoPodeSerNuloException() {
        super("CPF nao pode ser nulo.");
    }
}
