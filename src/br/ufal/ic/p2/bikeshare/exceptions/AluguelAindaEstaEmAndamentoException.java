package br.ufal.ic.p2.bikeshare.exceptions;

public class AluguelAindaEstaEmAndamentoException extends BikeshareException {
    public AluguelAindaEstaEmAndamentoException() {
        super("Aluguel ainda esta em andamento.");
    }
}
