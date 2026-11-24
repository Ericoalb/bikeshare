package br.ufal.ic.p2.bikeshare.exceptions;

public class IdentificacaoBicicletaNaoPodeSerNulaException extends BikeshareException {
    public IdentificacaoBicicletaNaoPodeSerNulaException() {
        super("Identificacao da bicicleta nao pode ser nula.");
    }
}
