package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.Plano;
import br.ufal.ic.p2.bikeshare.models.Usuario;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class EstadoSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<String, Usuario> usuarios;
    private final int proximoIdUsuario;
    private final Map<String, Estacao> estacoes;
    private final int proximoIdEstacao;
    private final Map<String, Bicicleta> bicicletas;
    private final Map<String, Aluguel> alugueis;
    private final int proximoIdAluguel;
    private final Map<String, Plano> planos;

    public EstadoSistema(Map<String, Usuario> usuarios, int proximoIdUsuario,
                         Map<String, Estacao> estacoes, int proximoIdEstacao,
                         Map<String, Bicicleta> bicicletas,
                         Map<String, Aluguel> alugueis, int proximoIdAluguel,
                         Map<String, Plano> planos) {
        this.usuarios = (usuarios != null) ? new LinkedHashMap<>(usuarios) : new LinkedHashMap<>();
        this.proximoIdUsuario = proximoIdUsuario;
        this.estacoes = new LinkedHashMap<>();
        if (estacoes != null) {
            for (Map.Entry<String, Estacao> entry : estacoes.entrySet()) {
                Estacao e = entry.getValue();
                Estacao clone = new Estacao(e.getId(), e.getNome(), e.getEndereco(), e.getCapacidade());
                for (String bId : e.getBicicletasIds()) {
                    clone.adicionarBicicleta(bId);
                }
                this.estacoes.put(entry.getKey(), clone);
            }
        }
        this.proximoIdEstacao = proximoIdEstacao;
        this.bicicletas = (bicicletas != null) ? new LinkedHashMap<>(bicicletas) : new LinkedHashMap<>();
        this.alugueis = (alugueis != null) ? new LinkedHashMap<>(alugueis) : new LinkedHashMap<>();
        this.proximoIdAluguel = proximoIdAluguel;
        this.planos = (planos != null) ? new LinkedHashMap<>(planos) : new LinkedHashMap<>();
    }

    public Map<String, Usuario> getUsuarios() { return Collections.unmodifiableMap(usuarios); }
    public int getProximoIdUsuario() { return proximoIdUsuario; }
    public Map<String, Estacao> getEstacoes() { return Collections.unmodifiableMap(estacoes); }
    public int getProximoIdEstacao() { return proximoIdEstacao; }
    public Map<String, Bicicleta> getBicicletas() { return Collections.unmodifiableMap(bicicletas); }
    public Map<String, Aluguel> getAlugueis() { return Collections.unmodifiableMap(alugueis); }
    public int getProximoIdAluguel() { return proximoIdAluguel; }
    public Map<String, Plano> getPlanos() { return Collections.unmodifiableMap(planos); }
}
