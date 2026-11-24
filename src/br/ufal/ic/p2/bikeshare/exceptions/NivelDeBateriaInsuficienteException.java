package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelDeBateriaInsuficienteException extends BikeshareException {
    public NivelDeBateriaInsuficienteException() {
        super("Nivel de bateria insuficiente.");
    }
}
