package br.ufal.ic.p2.bikeshare.exceptions;

public class DataDeDevolucaoAnteriorADataDeRetiradaException extends BikeshareException {
    public DataDeDevolucaoAnteriorADataDeRetiradaException() {
        super("Data de devolucao anterior a data de retirada.");
    }
}
