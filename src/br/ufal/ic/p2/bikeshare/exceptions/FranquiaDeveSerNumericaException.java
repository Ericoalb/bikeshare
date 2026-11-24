package br.ufal.ic.p2.bikeshare.exceptions;

public class FranquiaDeveSerNumericaException extends BikeshareException {
    public FranquiaDeveSerNumericaException() {
        super("Franquia deve ser numerica.");
    }
}
