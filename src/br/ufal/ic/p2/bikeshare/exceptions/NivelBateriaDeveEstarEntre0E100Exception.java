package br.ufal.ic.p2.bikeshare.exceptions;

public class NivelBateriaDeveEstarEntre0E100Exception extends BikeshareException {
    public NivelBateriaDeveEstarEntre0E100Exception() {
        super("Nivel de bateria deve estar entre 0 e 100.");
    }
}
