package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.*;

public class BicicletaEletrica extends Bicicleta {
    private static final long serialVersionUID = 1L;

    public static final int NIVEL_MINIMO_BATERIA = 15;

    private int nivelBateria;

    public BicicletaEletrica(String id, String estacaoId, int nivelBateria) {
        super(id, "eletrica", estacaoId);
        this.nivelBateria = nivelBateria;
    }

    public int getNivelBateria() { return nivelBateria; }
    public void setNivelBateria(int nivelBateria) { this.nivelBateria = nivelBateria; }

    @Override
    public boolean isDisponivelParaUso() {
        return "disponivel".equals(getEstado()) && nivelBateria >= NIVEL_MINIMO_BATERIA;
    }

    @Override
    protected String getAtributoEspecifico(String atributo) throws BikeshareException {
        if ("nivelBateria".equals(atributo)) {
            return String.valueOf(nivelBateria);
        }
        throw new AtributoNaoExisteException();
    }
}
