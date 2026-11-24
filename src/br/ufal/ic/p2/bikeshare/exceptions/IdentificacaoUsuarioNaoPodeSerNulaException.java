package br.ufal.ic.p2.bikeshare.exceptions;

public class IdentificacaoUsuarioNaoPodeSerNulaException extends BikeshareException {
    public IdentificacaoUsuarioNaoPodeSerNulaException() {
        super("Identificacao do usuario nao pode ser nula.");
    }
}
