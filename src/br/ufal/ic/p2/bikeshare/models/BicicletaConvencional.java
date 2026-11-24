package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.*;

public class BicicletaConvencional extends Bicicleta {
    private static final long serialVersionUID = 1L;

    public BicicletaConvencional(String id, String estacaoId) {
        super(id, "convencional", estacaoId);
    }

    @Override
    public boolean isDisponivelParaUso() {
        return "disponivel".equals(getEstado());
    }

    @Override
    protected String getAtributoEspecifico(String atributo) throws BikeshareException {
        if ("nivelBateria".equals(atributo)) {
            throw new BicicletaNaoPossuiBateriaException();
        }
        throw new AtributoNaoExisteException();
    }
}
