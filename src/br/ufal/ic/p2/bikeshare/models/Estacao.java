package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Estacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String endereco;
    private int capacidade;
    private List<String> bicicletasIds = new ArrayList<>();

    public Estacao(String id, String nome, String endereco, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.capacidade = capacidade;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    public List<String> getBicicletasIds() {
        return Collections.unmodifiableList(bicicletasIds);
    }

    public void adicionarBicicleta(String bicicletaId) {
        if (!bicicletasIds.contains(bicicletaId)) {
            bicicletasIds.add(bicicletaId);
        }
    }

    public void removerBicicleta(String bicicletaId) {
        bicicletasIds.remove(bicicletaId);
    }

    public int getVagasDisponiveis() {
        return capacidade - bicicletasIds.size();
    }
}
