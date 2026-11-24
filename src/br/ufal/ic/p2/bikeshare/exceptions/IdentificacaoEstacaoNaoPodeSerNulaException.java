package br.ufal.ic.p2.bikeshare.exceptions;

public class IdentificacaoEstacaoNaoPodeSerNulaException extends BikeshareException {
    public IdentificacaoEstacaoNaoPodeSerNulaException() {
        super("Identificacao da estacao nao pode ser nula.");
    }
}
