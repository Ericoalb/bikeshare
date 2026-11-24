package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.*;

public class UsuarioPessoaFisica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cpf;

    public UsuarioPessoaFisica(String id, String nome, String email, String senha, String cpf, String plano, Double mensalidade) {
        super(id, nome, email, senha, "pessoaFisica", plano, mensalidade);
        this.cpf = cpf;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    protected String getAtributoEspecifico(String atributo) throws BikeshareException {
        if ("cpf".equals(atributo)) {
            return cpf;
        }
        if ("cnpj".equals(atributo) || "razaoSocial".equals(atributo)) {
            throw new UsuarioNaoEhPessoaJuridicaException();
        }
        throw new AtributoNaoExisteException();
    }
}
