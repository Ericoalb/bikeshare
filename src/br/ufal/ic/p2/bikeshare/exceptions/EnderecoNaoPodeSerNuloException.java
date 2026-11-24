package br.ufal.ic.p2.bikeshare.exceptions;

public class EnderecoNaoPodeSerNuloException extends BikeshareException {
    public EnderecoNaoPodeSerNuloException() {
        super("Endereco nao pode ser nulo.");
    }
}
