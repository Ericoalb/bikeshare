package br.ufal.ic.p2.bikeshare.exceptions;

public class ValorDoMinutoExcedenteNaoPodeSerNuloException extends BikeshareException {
    public ValorDoMinutoExcedenteNaoPodeSerNuloException() {
        super("Valor do minuto excedente nao pode ser nulo.");
    }
}
