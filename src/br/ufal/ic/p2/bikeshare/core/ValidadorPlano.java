package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import br.ufal.ic.p2.bikeshare.models.Plano;
import java.util.Map;

public class ValidadorPlano {

    public static Plano validarECriarPlano(String nome, String franquiaMinutos, String valorMinutoExcedente, String mensalidade, Map<String, Plano> planos) throws BikeshareException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeDoPlanoNaoPodeSerNuloException();
        }
        String nomeTrim = nome.trim();
        if ("avulso".equalsIgnoreCase(nomeTrim) || "assinante".equalsIgnoreCase(nomeTrim)) {
            throw new PlanoJaExisteException();
        }
        for (String existing : planos.keySet()) {
            if (existing.equalsIgnoreCase(nomeTrim)) {
                throw new PlanoJaExisteException();
            }
        }

        if (franquiaMinutos == null || franquiaMinutos.trim().isEmpty()) {
            throw new FranquiaNaoPodeSerNulaException();
        }
        String fTrim = franquiaMinutos.trim();
        if (!fTrim.matches("^[+-]?\\d+$")) {
            throw new FranquiaDeveSerNumericaException();
        }
        int franquia;
        try {
            franquia = Integer.parseInt(fTrim);
        } catch (NumberFormatException e) {
            throw new FranquiaDeveSerNumericaException();
        }
        if (franquia < 0) {
            throw new FranquiaDeveSerNaoNegativaException();
        }

        if (valorMinutoExcedente == null || valorMinutoExcedente.trim().isEmpty()) {
            throw new ValorDoMinutoExcedenteNaoPodeSerNuloException();
        }
        double excedente;
        try {
            excedente = Double.parseDouble(valorMinutoExcedente.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new ValorDoMinutoExcedenteDeveSerNumericoException();
        }
        if (excedente < 0) {
            throw new ValorDoMinutoExcedenteDeveSerNaoNegativoException();
        }

        if (mensalidade == null || mensalidade.trim().isEmpty()) {
            throw new MensalidadeNaoPodeSerNulaException();
        }
        double mens;
        try {
            mens = Double.parseDouble(mensalidade.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new MensalidadeDeveSerNumericaException();
        }
        if (mens < 0) {
            throw new MensalidadeDeveSerNaoNegativaException();
        }

        return new Plano(nomeTrim, franquia, excedente, mens);
    }
}
