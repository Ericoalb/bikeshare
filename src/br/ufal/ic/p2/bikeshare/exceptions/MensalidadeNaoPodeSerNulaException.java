package br.ufal.ic.p2.bikeshare.exceptions;

public class MensalidadeNaoPodeSerNulaException extends BikeshareException {
    public MensalidadeNaoPodeSerNulaException() {
        super("Mensalidade nao pode ser nula.");
    }
}
