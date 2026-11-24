package br.ufal.ic.p2.bikeshare.exceptions;

public class PlanoJaExisteException extends BikeshareException {
    public PlanoJaExisteException() {
        super("Plano ja existe.");
    }
}
