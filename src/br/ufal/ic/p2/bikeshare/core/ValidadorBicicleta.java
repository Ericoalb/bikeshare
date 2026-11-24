package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import java.util.Map;

public class ValidadorBicicleta {

    public static Estacao validarEstacao(String estacaoId, Map<String, Estacao> estacoes) throws BikeshareException {
        if (estacaoId == null || estacaoId.trim().isEmpty()) {
            throw new IdentificacaoEstacaoNaoPodeSerNulaException();
        }
        Estacao estacao = estacoes.get(estacaoId.trim());
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return estacao;
    }

    public static void validarBicicletaId(String bicicletaId) throws BikeshareException {
        if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
            throw new IdentificacaoBicicletaNaoPodeSerNulaException();
        }
    }

    public static void validarBicicletaNova(String bicicletaId, Map<String, Bicicleta> bicicletas) throws BikeshareException {
        validarBicicletaId(bicicletaId);
        if (bicicletas.containsKey(bicicletaId.trim())) {
            throw new BicicletaJaExisteException();
        }
    }

    public static int validarNivelBateria(String nivelBateria) throws BikeshareException {
        if (nivelBateria == null || nivelBateria.trim().isEmpty()) {
            throw new NivelBateriaNaoPodeSerNuloException();
        }
        String bat = nivelBateria.trim();
        if (!bat.matches("^[+-]?\\d+$")) {
            throw new NivelBateriaDeveSerNumericoException();
        }
        int valor;
        try {
            valor = Integer.parseInt(bat);
        } catch (NumberFormatException e) {
            throw new NivelBateriaDeveSerNumericoException();
        }
        if (valor < 0 || valor > 100) {
            throw new NivelBateriaDeveEstarEntre0E100Exception();
        }
        return valor;
    }
}
