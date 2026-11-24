package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.exceptions.*;

public class ValidadorEstacao {
    public static void validarNome(String nome) throws BikeshareException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeNaoPodeSerNuloException();
        }
    }

    public static void validarEndereco(String endereco) throws BikeshareException {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EnderecoNaoPodeSerNuloException();
        }
    }

    public static int validarCapacidade(String capacidadeStr) throws BikeshareException {
        if (capacidadeStr == null || capacidadeStr.trim().isEmpty()) {
            throw new CapacidadeNaoPodeSerNulaException();
        }
        String cap = capacidadeStr.trim();
        if (!cap.matches("^[+-]?\\d+$")) {
            throw new CapacidadeDeveSerNumericaException();
        }
        int valor;
        try {
            valor = Integer.parseInt(cap);
        } catch (NumberFormatException e) {
            throw new CapacidadeDeveSerNumericaException();
        }
        if (valor <= 0) {
            throw new CapacidadeDeveSerPositivaException();
        }
        return valor;
    }
}
