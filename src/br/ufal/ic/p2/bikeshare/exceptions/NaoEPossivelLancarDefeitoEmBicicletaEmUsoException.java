package br.ufal.ic.p2.bikeshare.exceptions;

public class NaoEPossivelLancarDefeitoEmBicicletaEmUsoException extends BikeshareException {
    public NaoEPossivelLancarDefeitoEmBicicletaEmUsoException() {
        super("Nao e possivel lancar defeito em bicicleta em uso.");
    }
}
