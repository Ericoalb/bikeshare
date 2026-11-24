package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorNaoPodeSerNuloException extends BikeshareException {
    public ValorNaoPodeSerNuloException() {
        super("Valor nao pode ser nulo.");
    }
}
