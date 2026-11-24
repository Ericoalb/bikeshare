package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import java.io.Serializable;
import java.util.Locale;

public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private String estado = "ativo";
    private String plano = "avulso";
    private Double mensalidade = null;
    private String metodoPagamento = "nenhum";
    private String aluguelAtivo = "nenhum";
    private double totalPendencias = 0.0;
    private java.util.List<Penalidade> penalidades = new java.util.ArrayList<>();

    private double saldoPreCarregado = 0.0;
    private String numeroCartao = null;
    private String validadeCartao = null;
    private String chavePix = null;

    public Usuario(String id, String nome, String email, String senha, String tipo, String plano, Double mensalidade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.plano = (plano == null || plano.trim().isEmpty()) ? "avulso" : plano;
        this.mensalidade = mensalidade;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getPlano() { return plano; }
    public void setPlano(String plano) { this.plano = plano; }
    public Double getMensalidade() { return mensalidade; }
    public void setMensalidade(Double mensalidade) { this.mensalidade = mensalidade; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
    public String getAluguelAtivo() { return aluguelAtivo; }
    public void setAluguelAtivo(String aluguelAtivo) { this.aluguelAtivo = aluguelAtivo; }
    public double getTotalPendencias() { return totalPendencias; }
    public void setTotalPendencias(double totalPendencias) { this.totalPendencias = totalPendencias; }

    public java.util.List<Penalidade> getPenalidades() {
        if (penalidades == null) {
            penalidades = new java.util.ArrayList<>();
        }
        return java.util.Collections.unmodifiableList(penalidades);
    }

    public void adicionarPenalidade(Penalidade p) {
        if (penalidades == null) {
            penalidades = new java.util.ArrayList<>();
        }
        penalidades.add(p);
        this.totalPendencias += p.getValor();
        this.estado = "inadimplente";
    }

    public double getSaldoPreCarregado() { return saldoPreCarregado; }
    public void setSaldoPreCarregado(double saldoPreCarregado) { this.saldoPreCarregado = saldoPreCarregado; }
    public void adicionarSaldo(double valor) { this.saldoPreCarregado += valor; }

    public String getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(String numeroCartao) { this.numeroCartao = numeroCartao; }

    public String getValidadeCartao() { return validadeCartao; }
    public void setValidadeCartao(String validadeCartao) { this.validadeCartao = validadeCartao; }

    public String getChavePix() { return chavePix; }
    public void setChavePix(String chavePix) { this.chavePix = chavePix; }

    public String getAtributo(String atributo) throws BikeshareException {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "nome":
                return getNome();
            case "email":
                return getEmail();
            case "senha":
                return getSenha();
            case "tipo":
                return getTipo();
            case "estado":
                return getEstado();
            case "plano":
                return getPlano();
            case "metodoPagamento":
                return getMetodoPagamento();
            case "aluguelAtivo":
                return getAluguelAtivo();
            case "totalPendencias":
                return String.format(Locale.GERMAN, "%.2f", totalPendencias);
            case "mensalidade":
                if ("avulso".equals(plano) || mensalidade == null) {
                    throw new PlanoNaoPossuiMensalidadeException();
                }
                return String.format(Locale.GERMAN, "%.2f", mensalidade);
            case "numeroCartao":
                if (!"cartao".equals(metodoPagamento)) {
                    throw new UsuarioNaoUsaCartaoException();
                }
                return numeroCartao;
            case "validadeCartao":
                if (!"cartao".equals(metodoPagamento)) {
                    throw new UsuarioNaoUsaCartaoException();
                }
                return validadeCartao;
            case "chavePix":
                if (!"pix".equals(metodoPagamento)) {
                    throw new UsuarioNaoUsaPixException();
                }
                return chavePix;
            case "saldo":
                if (!"saldoPreCarregado".equals(metodoPagamento)) {
                    throw new UsuarioNaoUsaSaldoPreCarregadoException();
                }
                return String.format(Locale.GERMAN, "%.2f", saldoPreCarregado);
            default:
                return getAtributoEspecifico(atributo);
        }
    }

    protected abstract String getAtributoEspecifico(String atributo) throws BikeshareException;
}
