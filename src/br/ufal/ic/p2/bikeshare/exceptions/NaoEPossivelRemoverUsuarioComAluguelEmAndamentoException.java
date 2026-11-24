package br.ufal.ic.p2.bikeshare.exceptions;

public class NaoEPossivelRemoverUsuarioComAluguelEmAndamentoException extends BikeshareException {
    public NaoEPossivelRemoverUsuarioComAluguelEmAndamentoException() {
        super("Nao e possivel remover usuario com aluguel em andamento.");
    }
}
