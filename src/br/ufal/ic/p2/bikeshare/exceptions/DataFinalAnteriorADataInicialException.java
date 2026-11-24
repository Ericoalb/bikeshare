package br.ufal.ic.p2.bikeshare.exceptions;

public class DataFinalAnteriorADataInicialException extends BikeshareException {
    public DataFinalAnteriorADataInicialException() {
        super("Data final anterior a data inicial.");
    }
}
