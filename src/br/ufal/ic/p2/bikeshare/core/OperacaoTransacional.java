package br.ufal.ic.p2.bikeshare.core;

@FunctionalInterface
public interface OperacaoTransacional<T> {
    T executar() throws Exception;
}
