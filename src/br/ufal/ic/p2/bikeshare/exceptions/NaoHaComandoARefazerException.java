package br.ufal.ic.p2.bikeshare.exceptions;

public class NaoHaComandoARefazerException extends BikeshareException {
    public NaoHaComandoARefazerException() {
        super("Nao ha comando a refazer.");
    }
}
