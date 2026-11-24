package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelBateriaDeveSerNumericoException extends BikeshareException {
    public NivelBateriaDeveSerNumericoException() {
        super("Nivel de bateria deve ser numerico.");
    }
}
