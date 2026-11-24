package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelBateriaNaoPodeSerNuloException extends BikeshareException {
    public NivelBateriaNaoPodeSerNuloException() {
        super("Nivel de bateria nao pode ser nulo.");
    }
}
