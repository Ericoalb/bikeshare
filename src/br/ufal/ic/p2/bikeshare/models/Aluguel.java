package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Aluguel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String usuarioId;
    private String bicicletaId;
    private String estacaoOrigemId;
    private String estacaoDestinoId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String estado = "emAndamento"; // emAndamento, finalizado, cancelado, expirado
    private Double valorCobrado = null;

    public Aluguel(String id, String usuarioId, String bicicletaId, String estacaoOrigemId, LocalDateTime dataInicio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.bicicletaId = bicicletaId;
        this.estacaoOrigemId = estacaoOrigemId;
        this.dataInicio = dataInicio;
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getBicicletaId() { return bicicletaId; }
    public String getEstacaoOrigemId() { return estacaoOrigemId; }
    public String getEstacaoDestinoId() { return estacaoDestinoId; }
    public void setEstacaoDestinoId(String estacaoDestinoId) { this.estacaoDestinoId = estacaoDestinoId; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Double getValorCobrado() { return valorCobrado; }
    public void setValorCobrado(Double valorCobrado) { this.valorCobrado = valorCobrado; }

    public static String formatarData(LocalDateTime dt) {
        if (dt == null) return "";
        return dt.format(DateTimeFormatter.ofPattern("d/M/yyyy H:mm"));
    }

    public String getAtributo(String atributo) throws BikeshareException {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "estado":
                return getEstado();
            case "usuario":
                return getUsuarioId();
            case "bicicleta":
                return getBicicletaId();
            case "estacaoOrigem":
                return getEstacaoOrigemId();
            case "inicio":
                return formatarData(dataInicio);
            case "fim":
                if ("emAndamento".equals(estado)) {
                    throw new AluguelAindaEstaEmAndamentoException();
                }
                return formatarData(dataFim);
            case "estacaoDestino":
                if ("emAndamento".equals(estado)) {
                    throw new AluguelAindaEstaEmAndamentoException();
                }
                return estacaoDestinoId;
            case "valorCobrado":
                if ("emAndamento".equals(estado)) {
                    throw new AluguelAindaEstaEmAndamentoException();
                }
                return String.format(Locale.GERMAN, "%.2f", valorCobrado != null ? valorCobrado : 0.0);
            default:
                throw new AtributoNaoExisteException();
        }
    }
}
