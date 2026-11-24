package br.ufal.ic.p2.bikeshare.exceptions;

public class DescricaoNaoPodeSerNulaException extends BikeshareException {
    public DescricaoNaoPodeSerNulaException() {
        super("Descricao nao pode ser nula.");
    }
}
