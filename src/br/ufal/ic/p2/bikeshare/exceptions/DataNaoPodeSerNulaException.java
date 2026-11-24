package br.ufal.ic.p2.bikeshare.exceptions;

public class DataNaoPodeSerNulaException extends BikeshareException {
    public DataNaoPodeSerNulaException() {
        super("Data nao pode ser nula.");
    }
}
