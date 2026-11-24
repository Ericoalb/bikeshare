package br.ufal.ic.p2.bikeshare.exceptions;

public class PlanoNaoPossuiMensalidadeException extends BikeshareException {
    public PlanoNaoPossuiMensalidadeException() {
        super("Plano nao possui mensalidade.");
    }
}
