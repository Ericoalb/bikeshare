package br.ufal.ic.p2.bikeshare.exceptions;

public class MensalidadeDeveSerNaoNegativaException extends BikeshareException {
    public MensalidadeDeveSerNaoNegativaException() {
        super("Mensalidade deve ser nao-negativa.");
    }
}
