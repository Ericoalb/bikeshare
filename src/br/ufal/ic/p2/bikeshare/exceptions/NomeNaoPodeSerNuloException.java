package br.ufal.ic.p2.bikeshare.exceptions;

public class NomeNaoPodeSerNuloException extends BikeshareException {
    public NomeNaoPodeSerNuloException() {
        super("Nome nao pode ser nulo.");
    }
}
