package br.ufal.ic.p2.bikeshare.exceptions;

public class IdentificacaoAluguelNaoPodeSerNulaException extends BikeshareException {
    public IdentificacaoAluguelNaoPodeSerNulaException() {
        super("Identificacao do aluguel nao pode ser nula.");
    }
}
