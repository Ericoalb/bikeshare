package br.ufal.ic.p2.bikeshare.exceptions;

public class MetodoDePagamentoNaoCadastradoException extends BikeshareException {
    public MetodoDePagamentoNaoCadastradoException() {
        super("Metodo de pagamento nao cadastrado.");
    }
}
