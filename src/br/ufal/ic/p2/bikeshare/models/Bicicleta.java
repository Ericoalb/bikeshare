package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import java.io.Serializable;

public abstract class Bicicleta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String tipo;
    private String estado = "disponivel";
    private String estacaoId;

    public Bicicleta(String id, String tipo, String estacaoId) {
        this.id = id;
        this.tipo = tipo;
        this.estacaoId = estacaoId;
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getEstacaoId() { return estacaoId; }
    public void setEstacaoId(String estacaoId) { this.estacaoId = estacaoId; }

    public abstract boolean isDisponivelParaUso();

    public String getAtributo(String atributo) throws BikeshareException {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "tipo":
                return getTipo();
            case "estado":
                return getEstado();
            case "estacao":
                return getEstacaoId();
            default:
                return getAtributoEspecifico(atributo);
        }
    }

    protected abstract String getAtributoEspecifico(String atributo) throws BikeshareException;
}
