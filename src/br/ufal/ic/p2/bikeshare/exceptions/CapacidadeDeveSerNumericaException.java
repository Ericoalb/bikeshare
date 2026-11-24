package br.ufal.ic.p2.bikeshare.exceptions;

public class CapacidadeDeveSerNumericaException extends BikeshareException {
    public CapacidadeDeveSerNumericaException() {
        super("Capacidade deve ser numerica.");
    }
}
