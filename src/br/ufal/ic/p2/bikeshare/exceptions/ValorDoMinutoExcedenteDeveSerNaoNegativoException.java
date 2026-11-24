package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorDoMinutoExcedenteDeveSerNaoNegativoException extends BikeshareException {
    public ValorDoMinutoExcedenteDeveSerNaoNegativoException() {
        super("Valor do minuto excedente deve ser nao-negativo.");
    }
}
