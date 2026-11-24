package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorDoMinutoExcedenteDeveSerNumericoException extends BikeshareException {
    public ValorDoMinutoExcedenteDeveSerNumericoException() {
        super("Valor do minuto excedente deve ser numerico.");
    }
}
