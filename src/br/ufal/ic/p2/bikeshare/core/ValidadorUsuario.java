package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import br.ufal.ic.p2.bikeshare.models.Usuario;
import java.util.Map;

public class ValidadorUsuario {

    public static void validarNome(String nome) throws BikeshareException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeNaoPodeSerNuloException();
        }
    }

    public static void validarEmail(String email, Map<String, Usuario> usuariosExistentes, String idUsuarioIgnorado) throws BikeshareException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailNaoPodeSerNuloException();
        }
        if (!email.contains("@")) {
            throw new EmailInvalidoException();
        }
        for (Usuario u : usuariosExistentes.values()) {
            if (idUsuarioIgnorado != null && u.getId().equals(idUsuarioIgnorado)) {
                continue;
            }
            if (u.getEmail().equalsIgnoreCase(email.trim())) {
                throw new EmailJaCadastradoException();
            }
        }
    }

    public static void validarSenha(String senha) throws BikeshareException {
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaNaoPodeSerNulaException();
        }
    }

    public static void validarTipoEsperado(String tipo, String tipoEsperado) throws BikeshareException {
        if (tipo == null || !tipo.equals(tipoEsperado)) {
            throw new TipoInvalidoException();
        }
    }

    public static void validarCpf(String cpf) throws BikeshareException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new CpfNaoPodeSerNuloException();
        }
    }

    public static void validarCnpj(String cnpj) throws BikeshareException {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new CnpjNaoPodeSerNuloException();
        }
    }

    public static void validarRazaoSocial(String razaoSocial) throws BikeshareException {
        if (razaoSocial == null || razaoSocial.trim().isEmpty()) {
            throw new RazaoSocialNaoPodeSerNulaException();
        }
    }

    public static Double validarPlanoEMensalidade(String plano, String mensalidadeStr) throws BikeshareException {
        if (plano == null || plano.trim().isEmpty()) {
            throw new PlanoInvalidoException();
        }
        String p = plano.trim();
        if (!"avulso".equals(p) && !"assinante".equals(p)) {
            throw new PlanoInvalidoException();
        }

        if ("avulso".equals(p)) {
            if (mensalidadeStr != null && !mensalidadeStr.trim().isEmpty()) {
                throw new PlanoNaoPossuiMensalidadeException();
            }
            return null;
        }

        // Caso assinante
        if (mensalidadeStr == null || mensalidadeStr.trim().isEmpty()) {
            throw new MensalidadeNaoPodeSerNulaException();
        }

        double valor;
        try {
            valor = Double.parseDouble(mensalidadeStr.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new MensalidadeDeveSerNumericaException();
        }

        if (valor < 0) {
            throw new MensalidadeDeveSerNaoNegativaException();
        }

        return valor;
    }
}
