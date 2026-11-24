package br.ufal.ic.p2.bikeshare.exceptions;

public class CnpjNaoPodeSerNuloException extends BikeshareException {
    public CnpjNaoPodeSerNuloException() {
        super("CNPJ nao pode ser nulo.");
    }
}
