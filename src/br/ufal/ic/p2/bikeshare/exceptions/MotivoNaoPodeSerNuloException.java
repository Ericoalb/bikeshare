package br.ufal.ic.p2.bikeshare.exceptions;

public class MotivoNaoPodeSerNuloException extends BikeshareException {
    public MotivoNaoPodeSerNuloException() {
        super("Motivo nao pode ser nulo.");
    }
}
