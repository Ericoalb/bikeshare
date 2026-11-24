package br.ufal.ic.p2.bikeshare.exceptions;

public class NaoHaComandoADesfazerException extends BikeshareException {
    public NaoHaComandoADesfazerException() {
        super("Nao ha comando a desfazer.");
    }
}
