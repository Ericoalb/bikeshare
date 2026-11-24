package br.ufal.ic.p2.bikeshare.exceptions;

public class ChavePixNaoPodeSerNulaException extends BikeshareException {
    public ChavePixNaoPodeSerNulaException() {
        super("Chave pix nao pode ser nula.");
    }
}
