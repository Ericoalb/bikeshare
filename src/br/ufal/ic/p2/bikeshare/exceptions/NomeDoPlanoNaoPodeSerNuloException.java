package br.ufal.ic.p2.bikeshare.exceptions;

public class NomeDoPlanoNaoPodeSerNuloException extends BikeshareException {
    public NomeDoPlanoNaoPodeSerNuloException() {
        super("Nome do plano nao pode ser nulo.");
    }
}
