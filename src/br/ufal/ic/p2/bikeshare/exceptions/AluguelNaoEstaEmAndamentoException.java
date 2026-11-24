package br.ufal.ic.p2.bikeshare.exceptions;

public class AluguelNaoEstaEmAndamentoException extends BikeshareException {
    public AluguelNaoEstaEmAndamentoException() {
        super("Aluguel nao esta em andamento.");
    }
}
