package br.ufal.ic.p2.bikeshare.exceptions;

public class CapacidadeDeveSerPositivaException extends BikeshareException {
    public CapacidadeDeveSerPositivaException() {
        super("Capacidade deve ser positiva.");
    }
}
