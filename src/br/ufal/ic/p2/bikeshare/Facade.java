package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.core.Sistema;

public class Facade {

    private Sistema sistema = Sistema.carregar();

    public String criarUsuario(String nome, String email, String senha, String tipo, String cpfOuCnpj) throws Exception {
        return sistema.criarUsuario(nome, email, senha, tipo, cpfOuCnpj);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial) throws Exception {
        return sistema.criarUsuario(nome, email, senha, tipo, cnpj, razaoSocial);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf,
                                String plano, String mensalidade) throws Exception {
        return sistema.criarUsuario(nome, email, senha, tipo, cpf, plano, mensalidade);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj,
                                String razaoSocial, String plano, String mensalidade) throws Exception {
        return sistema.criarUsuario(nome, email, senha, tipo, cnpj, razaoSocial, plano, mensalidade);
    }

    public String getAtributoUsuario(String usuario, String atributo) throws Exception {
        return sistema.getAtributoUsuario(usuario, atributo);
    }

    public String getUsuarioPorEmail(String email) throws Exception {
        return sistema.getUsuarioPorEmail(email);
    }

    public void alterarUsuario(String usuario, String atributo, String valor) throws Exception {
        sistema.alterarUsuario(usuario, atributo, valor);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String chave) throws Exception {
        sistema.alterarUsuario(usuario, atributo, valor1, chave);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String numero, String validade) throws Exception {
        sistema.alterarUsuario(usuario, atributo, valor1, numero, validade);
    }

    public void removerUsuario(String usuario) throws Exception {
        sistema.removerUsuario(usuario);
    }

    public String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
        return sistema.criarEstacao(nome, endereco, capacidade);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) throws Exception {
        sistema.adicionarBicicleta(estacao, bicicleta, tipo);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) throws Exception {
        sistema.adicionarBicicleta(estacao, bicicleta, tipo, nivelBateria);
    }

    public void removerBicicleta(String estacao, String bicicleta) throws Exception {
        sistema.removerBicicleta(estacao, bicicleta);
    }

    public String getAtributoBicicleta(String bicicleta, String atributo) throws Exception {
        return sistema.getAtributoBicicleta(bicicleta, atributo);
    }

    public String getBicicletasDisponiveis(String estacao) throws Exception {
        return sistema.getBicicletasDisponiveis(estacao);
    }

    public String getVagasDisponiveis(String estacao) throws Exception {
        return sistema.getVagasDisponiveis(estacao);
    }

    public String getEstadoBicicleta(String bicicleta) throws Exception {
        return sistema.getEstadoBicicleta(bicicleta);
    }

    public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data) throws Exception {
        return sistema.retirarBicicleta(usuario, estacao, bicicleta, data);
    }

    public void devolverBicicleta(String aluguel, String estacao, String data) throws Exception {
        sistema.devolverBicicleta(aluguel, estacao, data);
    }

    public String getHistoricoAlugueis(String usuario) throws Exception {
        return sistema.getHistoricoAlugueis(usuario);
    }

    public String getHistoricoAlugueis(String usuario, String atributo) throws Exception {
        return sistema.getHistoricoAlugueis(usuario, atributo);
    }

    public String getAtributoAluguel(String aluguel, String atributo) throws Exception {
        return sistema.getAtributoAluguel(aluguel, atributo);
    }

    public String criarPlano(String nome, String franquiaMinutos, String valorMinutoExcedente, String mensalidade) throws Exception {
        return sistema.criarPlano(nome, franquiaMinutos, valorMinutoExcedente, mensalidade);
    }

    public void lancaPenalidade(String usuario, String tipo, String valor, String motivo) throws Exception {
        sistema.lancaPenalidade(usuario, tipo, valor, motivo);
    }

    public void lancaDefeito(String bicicleta, String descricao) throws Exception {
        sistema.lancaDefeito(bicicleta, descricao);
    }

    public void concluiManutencao(String bicicleta) throws Exception {
        sistema.concluiManutencao(bicicleta);
    }

    public String getTotalFaturado(String dataInicial, String dataFinal) throws Exception {
        return sistema.getTotalFaturado(dataInicial, dataFinal);
    }

    public void undo() throws Exception {
        sistema.undo();
    }

    public void redo() throws Exception {
        sistema.redo();
    }

    public void zerarSistema() throws Exception {
        sistema.zerarSistema();
    }

    public void encerrarSistema() throws Exception {
        sistema.encerrarSistema();
    }
}
