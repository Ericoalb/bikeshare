package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorDeveSerPositivoException extends BikeshareException {
    public ValorDeveSerPositivoException() {
        super("Valor deve ser positivo.");
    }
}
