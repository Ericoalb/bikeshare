package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorDeveSerNumericoException extends BikeshareException {
    public ValorDeveSerNumericoException() {
        super("Valor deve ser numerico.");
    }
}
