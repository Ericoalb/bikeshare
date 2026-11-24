package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.*;

public class UsuarioPessoaJuridica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cnpj;
    private String razaoSocial;

    public UsuarioPessoaJuridica(String id, String nome, String email, String senha, String cnpj, String razaoSocial, String plano, Double mensalidade) {
        super(id, nome, email, senha, "pessoaJuridica", plano, mensalidade);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    @Override
    protected String getAtributoEspecifico(String atributo) throws BikeshareException {
        if ("cnpj".equals(atributo)) {
            return cnpj;
        }
        if ("razaoSocial".equals(atributo)) {
            return razaoSocial;
        }
        if ("cpf".equals(atributo)) {
            throw new UsuarioNaoEhPessoaFisicaException();
        }
        throw new AtributoNaoExisteException();
    }
}
