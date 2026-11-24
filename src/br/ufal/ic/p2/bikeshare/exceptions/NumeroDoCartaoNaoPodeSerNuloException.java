package br.ufal.ic.p2.bikeshare.exceptions;

public class NumeroDoCartaoNaoPodeSerNuloException extends BikeshareException {
    public NumeroDoCartaoNaoPodeSerNuloException() {
        super("Numero do cartao nao pode ser nulo.");
    }
}
