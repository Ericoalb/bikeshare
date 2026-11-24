package br.ufal.ic.p2.bikeshare.exceptions;

public class FranquiaDeveSerNaoNegativaException extends BikeshareException {
    public FranquiaDeveSerNaoNegativaException() {
        super("Franquia deve ser nao-negativa.");
    }
}
