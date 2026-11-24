package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioNaoUsaSaldoPreCarregadoException extends BikeshareException {
    public UsuarioNaoUsaSaldoPreCarregadoException() {
        super("Usuario nao usa saldo pre-carregado.");
    }
}
