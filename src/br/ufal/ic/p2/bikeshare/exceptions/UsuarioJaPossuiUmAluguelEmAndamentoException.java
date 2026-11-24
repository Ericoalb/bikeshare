package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioJaPossuiUmAluguelEmAndamentoException extends BikeshareException {
    public UsuarioJaPossuiUmAluguelEmAndamentoException() {
        super("Usuario ja possui um aluguel em andamento.");
    }
}
