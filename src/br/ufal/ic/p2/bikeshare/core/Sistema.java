package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.exceptions.*;
import br.ufal.ic.p2.bikeshare.models.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Sistema implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Usuario> usuarios = new LinkedHashMap<>();
    private int proximoIdUsuario = 1;
    private Map<String, Estacao> estacoes = new LinkedHashMap<>();
    private int proximoIdEstacao = 1;
    private Map<String, Bicicleta> bicicletas = new LinkedHashMap<>();
    private Map<String, Aluguel> alugueis = new LinkedHashMap<>();
    private int proximoIdAluguel = 1;
    private Map<String, Plano> planos = new LinkedHashMap<>();

    private transient ControleTransacional controleTransacional = new ControleTransacional();

    public Sistema() {
        checkTransients();
    }

    public void checkTransients() {
        if (controleTransacional == null) {
            controleTransacional = new ControleTransacional();
        }
        if (usuarios == null) {
            usuarios = new LinkedHashMap<>();
        }
        if (estacoes == null) {
            estacoes = new LinkedHashMap<>();
        }
        if (bicicletas == null) {
            bicicletas = new LinkedHashMap<>();
        }
        if (alugueis == null) {
            alugueis = new LinkedHashMap<>();
        }
        if (planos == null) {
            planos = new LinkedHashMap<>();
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        controleTransacional = new ControleTransacional();
        if (usuarios == null) {
            usuarios = new LinkedHashMap<>();
        }
        if (estacoes == null) {
            estacoes = new LinkedHashMap<>();
        }
        if (bicicletas == null) {
            bicicletas = new LinkedHashMap<>();
        }
        if (alugueis == null) {
            alugueis = new LinkedHashMap<>();
        }
        if (planos == null) {
            planos = new LinkedHashMap<>();
        }
    }

    private byte[] clonarEstado() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(new EstadoSistema(usuarios, proximoIdUsuario, estacoes, proximoIdEstacao, bicicletas, alugueis, proximoIdAluguel, planos));
            oos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao clonar estado do sistema: " + e.getMessage(), e);
        }
    }

    private void restaurarEstado(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            EstadoSistema estado = (EstadoSistema) ois.readObject();
            this.usuarios = new LinkedHashMap<>(estado.getUsuarios());
            this.proximoIdUsuario = estado.getProximoIdUsuario();
            this.estacoes = new LinkedHashMap<>(estado.getEstacoes());
            this.proximoIdEstacao = estado.getProximoIdEstacao();
            this.bicicletas = new LinkedHashMap<>(estado.getBicicletas());
            this.alugueis = new LinkedHashMap<>(estado.getAlugueis());
            this.proximoIdAluguel = estado.getProximoIdAluguel();
            this.planos = new LinkedHashMap<>(estado.getPlanos());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao restaurar estado do sistema: " + e.getMessage(), e);
        }
    }

    @FunctionalInterface
    public interface ComandoSemRetorno {
        void executar() throws Exception;
    }

    public synchronized <T> T executarComandoModificador(OperacaoTransacional<T> comando) throws Exception {
        checkTransients();
        return controleTransacional.executar(comando, this::clonarEstado, this::restaurarEstado);
    }

    public synchronized void executarComandoModificador(ComandoSemRetorno comando) throws Exception {
        executarComandoModificador(() -> {
            comando.executar();
            return null;
        });
    }

    public synchronized void undo() throws Exception {
        checkTransients();
        controleTransacional.undo(this::clonarEstado, this::restaurarEstado);
    }

    public synchronized void redo() throws Exception {
        checkTransients();
        controleTransacional.redo(this::clonarEstado, this::restaurarEstado);
    }

    public synchronized void zerarSistema() throws Exception {
        executarComandoModificador(() -> {
            usuarios.clear();
            proximoIdUsuario = 1;
            estacoes.clear();
            proximoIdEstacao = 1;
            bicicletas.clear();
            alugueis.clear();
            proximoIdAluguel = 1;
            planos.clear();
        });
    }

    public synchronized void encerrarSistema() throws Exception {
        salvar(this);
    }

    // =========================================================================
    // US1 - Métodos de Criar Usuário
    // =========================================================================

    public synchronized String criarUsuario(String nome, String email, String senha, String tipo, String cpf) throws Exception {
        return executarComandoModificador(() -> {
            ValidadorUsuario.validarNome(nome);
            ValidadorUsuario.validarEmail(email, usuarios, null);
            ValidadorUsuario.validarSenha(senha);
            ValidadorUsuario.validarTipoEsperado(tipo, "pessoaFisica");
            ValidadorUsuario.validarCpf(cpf);

            String id = "U" + proximoIdUsuario++;
            UsuarioPessoaFisica usuario = new UsuarioPessoaFisica(id, nome.trim(), email.trim(), senha, cpf.trim(), "avulso", null);
            usuarios.put(id, usuario);
            return id;
        });
    }

    public synchronized String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial) throws Exception {
        return executarComandoModificador(() -> {
            ValidadorUsuario.validarNome(nome);
            ValidadorUsuario.validarEmail(email, usuarios, null);
            ValidadorUsuario.validarSenha(senha);
            ValidadorUsuario.validarTipoEsperado(tipo, "pessoaJuridica");
            ValidadorUsuario.validarCnpj(cnpj);
            ValidadorUsuario.validarRazaoSocial(razaoSocial);

            String id = "U" + proximoIdUsuario++;
            UsuarioPessoaJuridica usuario = new UsuarioPessoaJuridica(id, nome.trim(), email.trim(), senha, cnpj.trim(), razaoSocial.trim(), "avulso", null);
            usuarios.put(id, usuario);
            return id;
        });
    }

    public synchronized String criarUsuario(String nome, String email, String senha, String tipo, String cpf, String plano, String mensalidade) throws Exception {
        return executarComandoModificador(() -> {
            ValidadorUsuario.validarNome(nome);
            ValidadorUsuario.validarEmail(email, usuarios, null);
            ValidadorUsuario.validarSenha(senha);
            ValidadorUsuario.validarTipoEsperado(tipo, "pessoaFisica");
            ValidadorUsuario.validarCpf(cpf);
            Double valorMensalidade = ValidadorUsuario.validarPlanoEMensalidade(plano, mensalidade);

            String id = "U" + proximoIdUsuario++;
            UsuarioPessoaFisica usuario = new UsuarioPessoaFisica(id, nome.trim(), email.trim(), senha, cpf.trim(), plano.trim(), valorMensalidade);
            usuarios.put(id, usuario);
            return id;
        });
    }

    public synchronized String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial, String plano, String mensalidade) throws Exception {
        return executarComandoModificador(() -> {
            ValidadorUsuario.validarNome(nome);
            ValidadorUsuario.validarEmail(email, usuarios, null);
            ValidadorUsuario.validarSenha(senha);
            ValidadorUsuario.validarTipoEsperado(tipo, "pessoaJuridica");
            ValidadorUsuario.validarCnpj(cnpj);
            ValidadorUsuario.validarRazaoSocial(razaoSocial);
            Double valorMensalidade = ValidadorUsuario.validarPlanoEMensalidade(plano, mensalidade);

            String id = "U" + proximoIdUsuario++;
            UsuarioPessoaJuridica usuario = new UsuarioPessoaJuridica(id, nome.trim(), email.trim(), senha, cnpj.trim(), razaoSocial.trim(), plano.trim(), valorMensalidade);
            usuarios.put(id, usuario);
            return id;
        });
    }

    // =========================================================================
    // US1 e US2 - Consulta de Usuário
    // =========================================================================

    public synchronized String getAtributoUsuario(String usuarioId, String atributo) throws Exception {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IdentificacaoUsuarioNaoPodeSerNulaException();
        }
        Usuario usuario = usuarios.get(usuarioId.trim());
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }
        return usuario.getAtributo(atributo);
    }

    public synchronized String getUsuarioPorEmail(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailNaoPodeSerNuloException();
        }
        for (Usuario u : usuarios.values()) {
            if (u.getEmail().equalsIgnoreCase(email.trim())) {
                return u.getId();
            }
        }
        throw new UsuarioNaoExisteException();
    }

    // =========================================================================
    // US3 - Alteração de Usuário
    // =========================================================================

    public synchronized void alterarUsuario(String usuarioId, String atributo, String valor) throws Exception {
        executarComandoModificador(() -> {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                throw new IdentificacaoUsuarioNaoPodeSerNulaException();
            }
            Usuario usuario = usuarios.get(usuarioId.trim());
            if (usuario == null) {
                throw new UsuarioNaoExisteException();
            }

            if (atributo == null || atributo.trim().isEmpty()) {
                throw new AtributoNaoPodeSerAlteradoException();
            }

            switch (atributo.trim()) {
                case "nome":
                    ValidadorUsuario.validarNome(valor);
                    usuario.setNome(valor.trim());
                    break;
                case "email":
                    ValidadorUsuario.validarEmail(valor, usuarios, usuario.getId());
                    usuario.setEmail(valor.trim());
                    break;
                case "senha":
                    ValidadorUsuario.validarSenha(valor);
                    usuario.setSenha(valor);
                    break;
                case "plano":
                    if (valor == null || valor.trim().isEmpty()) {
                        throw new PlanoInvalidoException();
                    }
                    String p = valor.trim();
                    if ("avulso".equals(p)) {
                        usuario.setPlano("avulso");
                        usuario.setMensalidade(null);
                    } else if ("assinante".equals(p)) {
                        if (!"assinante".equals(usuario.getPlano())) {
                            usuario.setPlano("assinante");
                            usuario.setMensalidade(30.0);
                        }
                    } else if (planos.containsKey(p)) {
                        Plano planoCustom = planos.get(p);
                        usuario.setPlano(planoCustom.getNome());
                        usuario.setMensalidade(planoCustom.getMensalidade());
                    } else {
                        throw new PlanoInvalidoException();
                    }
                    break;
                case "metodoPagamento":
                    if (valor == null || valor.trim().isEmpty() || !"saldoPreCarregado".equals(valor.trim())) {
                        throw new ValorDeMetodoDePagamentoInvalidoException();
                    }
                    usuario.setMetodoPagamento("saldoPreCarregado");
                    break;
                case "saldo":
                    if (!"saldoPreCarregado".equals(usuario.getMetodoPagamento())) {
                        throw new UsuarioNaoUsaSaldoPreCarregadoException();
                    }
                    if (valor == null || valor.trim().isEmpty()) {
                        throw new ValorNaoPodeSerNuloException();
                    }
                    double val;
                    try {
                        val = Double.parseDouble(valor.trim().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        throw new ValorDeveSerNumericoException();
                    }
                    if (val <= 0) {
                        throw new ValorDeveSerPositivoException();
                    }
                    usuario.adicionarSaldo(val);
                    break;
                default:
                    throw new AtributoNaoPodeSerAlteradoException();
            }
        });
    }

    public synchronized void alterarUsuario(String usuarioId, String atributo, String valor1, String chave) throws Exception {
        executarComandoModificador(() -> {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                throw new IdentificacaoUsuarioNaoPodeSerNulaException();
            }
            Usuario usuario = usuarios.get(usuarioId.trim());
            if (usuario == null) {
                throw new UsuarioNaoExisteException();
            }

            if (!"metodoPagamento".equals(atributo)) {
                throw new AtributoNaoPodeSerAlteradoException();
            }

            if (!"pix".equals(valor1)) {
                throw new ValorDeMetodoDePagamentoInvalidoException();
            }

            if (chave == null || chave.trim().isEmpty()) {
                throw new ChavePixNaoPodeSerNulaException();
            }

            usuario.setMetodoPagamento("pix");
            usuario.setChavePix(chave.trim());
        });
    }

    public synchronized void alterarUsuario(String usuarioId, String atributo, String valor1, String numero, String validade) throws Exception {
        executarComandoModificador(() -> {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                throw new IdentificacaoUsuarioNaoPodeSerNulaException();
            }
            Usuario usuario = usuarios.get(usuarioId.trim());
            if (usuario == null) {
                throw new UsuarioNaoExisteException();
            }

            if (!"metodoPagamento".equals(atributo)) {
                throw new AtributoNaoPodeSerAlteradoException();
            }

            if (!"cartao".equals(valor1)) {
                throw new ValorDeMetodoDePagamentoInvalidoException();
            }

            if (numero == null || numero.trim().isEmpty()) {
                throw new NumeroDoCartaoNaoPodeSerNuloException();
            }

            if (validade == null || validade.trim().isEmpty()) {
                throw new ValidadeDoCartaoNaoPodeSerNulaException();
            }

            if (!validade.trim().matches("^(0[1-9]|1[0-2])/[0-9]{4}$")) {
                throw new ValidadeDoCartaoInvalidaException();
            }

            usuario.setMetodoPagamento("cartao");
            usuario.setNumeroCartao(numero.trim());
            usuario.setValidadeCartao(validade.trim());
        });
    }

    // =========================================================================
    // US4 - Remoção de Usuário
    // =========================================================================

    public synchronized void removerUsuario(String usuarioId) throws Exception {
        executarComandoModificador(() -> {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                throw new IdentificacaoUsuarioNaoPodeSerNulaException();
            }
            Usuario usuario = usuarios.get(usuarioId.trim());
            if (usuario == null) {
                throw new UsuarioNaoExisteException();
            }
            if (!"nenhum".equals(usuario.getAluguelAtivo())) {
                throw new NaoEPossivelRemoverUsuarioComAluguelEmAndamentoException();
            }
            usuarios.remove(usuarioId.trim());
        });
    }

    // =========================================================================
    // US5 - Cadastro e Consulta de Estação
    // =========================================================================

    public synchronized String criarEstacao(String nome, String endereco, String capacidade) throws Exception {
        return executarComandoModificador(() -> {
            ValidadorEstacao.validarNome(nome);
            ValidadorEstacao.validarEndereco(endereco);
            int cap = ValidadorEstacao.validarCapacidade(capacidade);

            String id = "E" + proximoIdEstacao++;
            Estacao estacao = new Estacao(id, nome.trim(), endereco.trim(), cap);
            estacoes.put(id, estacao);
            return id;
        });
    }

    public synchronized String getVagasDisponiveis(String estacaoId) throws Exception {
        if (estacaoId == null || estacaoId.trim().isEmpty()) {
            throw new IdentificacaoEstacaoNaoPodeSerNulaException();
        }
        Estacao estacao = estacoes.get(estacaoId.trim());
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return String.valueOf(estacao.getVagasDisponiveis());
    }

    public synchronized String getBicicletasDisponiveis(String estacaoId) throws Exception {
        if (estacaoId == null || estacaoId.trim().isEmpty()) {
            throw new IdentificacaoEstacaoNaoPodeSerNulaException();
        }
        Estacao estacao = estacoes.get(estacaoId.trim());
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        int count = 0;
        for (String bId : estacao.getBicicletasIds()) {
            Bicicleta b = bicicletas.get(bId);
            if (b != null && b.isDisponivelParaUso()) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    // =========================================================================
    // US6 e US7 - Gestão e Consulta de Bicicletas
    // =========================================================================

    public synchronized void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo) throws Exception {
        executarComandoModificador(() -> {
            Estacao estacao = ValidadorBicicleta.validarEstacao(estacaoId, estacoes);
            ValidadorBicicleta.validarBicicletaNova(bicicletaId, bicicletas);

            if ("eletrica".equals(tipo)) {
                throw new NivelBateriaNaoPodeSerNuloException();
            }
            if (!"convencional".equals(tipo)) {
                throw new TipoInvalidoException();
            }

            if (estacao.getVagasDisponiveis() <= 0) {
                throw new EstacaoSemVagasDisponiveisException();
            }

            BicicletaConvencional b = new BicicletaConvencional(bicicletaId.trim(), estacao.getId());
            bicicletas.put(b.getId(), b);
            estacao.adicionarBicicleta(b.getId());
        });
    }

    public synchronized void adicionarBicicleta(String estacaoId, String bicicletaId, String tipo, String nivelBateria) throws Exception {
        executarComandoModificador(() -> {
            Estacao estacao = ValidadorBicicleta.validarEstacao(estacaoId, estacoes);
            ValidadorBicicleta.validarBicicletaNova(bicicletaId, bicicletas);

            if ("convencional".equals(tipo)) {
                throw new BicicletaNaoPossuiBateriaException();
            }
            if (!"eletrica".equals(tipo)) {
                throw new TipoInvalidoException();
            }

            int bateria = ValidadorBicicleta.validarNivelBateria(nivelBateria);

            if (estacao.getVagasDisponiveis() <= 0) {
                throw new EstacaoSemVagasDisponiveisException();
            }

            BicicletaEletrica b = new BicicletaEletrica(bicicletaId.trim(), estacao.getId(), bateria);
            bicicletas.put(b.getId(), b);
            estacao.adicionarBicicleta(b.getId());
        });
    }

    public synchronized void removerBicicleta(String estacaoId, String bicicletaId) throws Exception {
        executarComandoModificador(() -> {
            Estacao estacao = ValidadorBicicleta.validarEstacao(estacaoId, estacoes);
            ValidadorBicicleta.validarBicicletaId(bicicletaId);
            Bicicleta b = bicicletas.get(bicicletaId.trim());
            if (b == null) {
                throw new BicicletaNaoExisteException();
            }
            if (!estacao.getBicicletasIds().contains(b.getId())) {
                throw new BicicletaNaoEstaNestaEstacaoException();
            }
            if (!"disponivel".equals(b.getEstado())) {
                throw new BicicletaNaoEstaDisponivelException();
            }
            estacao.removerBicicleta(b.getId());
            bicicletas.remove(b.getId());
        });
    }

    public synchronized String getAtributoBicicleta(String bicicletaId, String atributo) throws Exception {
        ValidadorBicicleta.validarBicicletaId(bicicletaId);
        Bicicleta b = bicicletas.get(bicicletaId.trim());
        if (b == null) {
            throw new BicicletaNaoExisteException();
        }
        return b.getAtributo(atributo);
    }

    public synchronized String getEstadoBicicleta(String bicicletaId) throws Exception {
        ValidadorBicicleta.validarBicicletaId(bicicletaId);
        Bicicleta b = bicicletas.get(bicicletaId.trim());
        if (b == null) {
            throw new BicicletaNaoExisteException();
        }
        return b.getEstado();
    }

    // =========================================================================
    // US8 e US9 - Retirada e Devolução de Bicicleta
    // =========================================================================

    public synchronized String retirarBicicleta(String usuarioId, String estacaoId, String bicicletaId, String dataStr) throws Exception {
        return executarComandoModificador(() -> {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                throw new IdentificacaoUsuarioNaoPodeSerNulaException();
            }
            Usuario usuario = usuarios.get(usuarioId.trim());
            if (usuario == null) {
                throw new UsuarioNaoExisteException();
            }

            if (estacaoId == null || estacaoId.trim().isEmpty()) {
                throw new IdentificacaoEstacaoNaoPodeSerNulaException();
            }
            Estacao estacao = estacoes.get(estacaoId.trim());
            if (estacao == null) {
                throw new EstacaoNaoExisteException();
            }

            if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
                throw new IdentificacaoBicicletaNaoPodeSerNulaException();
            }
            Bicicleta bike = bicicletas.get(bicicletaId.trim());
            if (bike == null) {
                throw new BicicletaNaoExisteException();
            }

            LocalDateTime dataInicio = ValidadorData.validarEConverter(dataStr);

            if ("inadimplente".equals(usuario.getEstado())) {
                throw new UsuarioInadimplenteException();
            }

            if (!"nenhum".equals(usuario.getAluguelAtivo())) {
                throw new UsuarioJaPossuiUmAluguelEmAndamentoException();
            }

            if (!estacao.getBicicletasIds().contains(bike.getId())) {
                throw new BicicletaNaoEstaNestaEstacaoException();
            }

            if (!"disponivel".equals(bike.getEstado())) {
                throw new BicicletaNaoEstaDisponivelException();
            }

            if (bike instanceof BicicletaEletrica) {
                BicicletaEletrica be = (BicicletaEletrica) bike;
                if (be.getNivelBateria() < BicicletaEletrica.NIVEL_MINIMO_BATERIA) {
                    throw new NivelDeBateriaInsuficienteException();
                }
            }

            if ("nenhum".equals(usuario.getMetodoPagamento())) {
                throw new MetodoDePagamentoNaoCadastradoException();
            }

            if ("saldoPreCarregado".equals(usuario.getMetodoPagamento()) && usuario.getSaldoPreCarregado() <= 0) {
                throw new SaldoInsuficienteException();
            }

            String aluguelId = "A" + proximoIdAluguel++;
            Aluguel aluguel = new Aluguel(aluguelId, usuario.getId(), bike.getId(), estacao.getId(), dataInicio);
            alugueis.put(aluguelId, aluguel);

            estacao.removerBicicleta(bike.getId());
            bike.setEstado("emUso");
            bike.setEstacaoId("nenhuma");
            usuario.setAluguelAtivo(aluguelId);

            return aluguelId;
        });
    }

    public synchronized void devolverBicicleta(String aluguelId, String estacaoId, String dataStr) throws Exception {
        executarComandoModificador(() -> {
            if (aluguelId == null || aluguelId.trim().isEmpty()) {
                throw new IdentificacaoAluguelNaoPodeSerNulaException();
            }
            Aluguel aluguel = alugueis.get(aluguelId.trim());
            if (aluguel == null) {
                throw new AluguelNaoExisteException();
            }

            if (estacaoId == null || estacaoId.trim().isEmpty()) {
                throw new IdentificacaoEstacaoNaoPodeSerNulaException();
            }
            Estacao estacaoDestino = estacoes.get(estacaoId.trim());
            if (estacaoDestino == null) {
                throw new EstacaoNaoExisteException();
            }

            LocalDateTime dataDevolucao = ValidadorData.validarEConverter(dataStr);

            if (!"emAndamento".equals(aluguel.getEstado())) {
                throw new AluguelNaoEstaEmAndamentoException();
            }

            if (dataDevolucao.isBefore(aluguel.getDataInicio())) {
                throw new DataDeDevolucaoAnteriorADataDeRetiradaException();
            }

            if (estacaoDestino.getVagasDisponiveis() <= 0) {
                throw new EstacaoCheiaException();
            }

            long minutos = java.time.Duration.between(aluguel.getDataInicio(), dataDevolucao).toMinutes();
            Usuario usuario = usuarios.get(aluguel.getUsuarioId());
            Bicicleta bike = bicicletas.get(aluguel.getBicicletaId());

            // 1. Expirado (> 1440 min)
            if (minutos > 1440) {
                aluguel.setEstado("expirado");
                aluguel.setDataFim(dataDevolucao);
                aluguel.setEstacaoDestinoId(estacaoDestino.getId());
                aluguel.setValorCobrado(0.0);

                bike.setEstado("emManutencao");
                bike.setEstacaoId(estacaoDestino.getId());
                estacaoDestino.adicionarBicicleta(bike.getId());

                usuario.adicionarPenalidade(new PenalidadeAtraso());
                usuario.setAluguelAtivo("nenhum");
                return;
            }

            // 2. Carencia (<= 2 min na mesma estacao)
            if (minutos <= 2 && estacaoDestino.getId().equals(aluguel.getEstacaoOrigemId())) {
                aluguel.setEstado("cancelado");
                aluguel.setDataFim(dataDevolucao);
                aluguel.setEstacaoDestinoId(estacaoDestino.getId());
                aluguel.setValorCobrado(0.0);

                bike.setEstado("disponivel");
                bike.setEstacaoId(estacaoDestino.getId());
                estacaoDestino.adicionarBicicleta(bike.getId());

                usuario.setAluguelAtivo("nenhum");
                return;
            }

            // 3. Calculo da tarifa
            double valorAdicionalEletrica = 0.0;
            if (bike instanceof BicicletaEletrica) {
                valorAdicionalEletrica = minutos * 0.20;
            }

            double valorBase = 0.0;
            if ("avulso".equals(usuario.getPlano())) {
                valorBase = minutos * 0.50;
            } else if ("assinante".equals(usuario.getPlano())) {
                long franquia = 60;
                long excedente = Math.max(0, minutos - franquia);
                valorBase = excedente * 0.30;
            } else if (planos.containsKey(usuario.getPlano())) {
                Plano p = planos.get(usuario.getPlano());
                long franquia = p.getFranquiaMinutos();
                long excedente = Math.max(0, minutos - franquia);
                valorBase = excedente * p.getValorMinutoExcedente();
            }

            double totalCobrado = valorBase + valorAdicionalEletrica;

            // Se pagamento com saldo, validar se tem saldo suficiente
            if ("saldoPreCarregado".equals(usuario.getMetodoPagamento())) {
                if (usuario.getSaldoPreCarregado() < totalCobrado) {
                    throw new SaldoInsuficienteException();
                }
                usuario.setSaldoPreCarregado(usuario.getSaldoPreCarregado() - totalCobrado);
            }

            aluguel.setEstado("finalizado");
            aluguel.setDataFim(dataDevolucao);
            aluguel.setEstacaoDestinoId(estacaoDestino.getId());
            aluguel.setValorCobrado(totalCobrado);

            bike.setEstado("disponivel");
            bike.setEstacaoId(estacaoDestino.getId());
            estacaoDestino.adicionarBicicleta(bike.getId());

            usuario.setAluguelAtivo("nenhum");
        });
    }

    public synchronized String getAtributoAluguel(String aluguelId, String atributo) throws Exception {
        if (aluguelId == null || aluguelId.trim().isEmpty()) {
            throw new IdentificacaoAluguelNaoPodeSerNulaException();
        }
        Aluguel aluguel = alugueis.get(aluguelId.trim());
        if (aluguel == null) {
            throw new AluguelNaoExisteException();
        }
        return aluguel.getAtributo(atributo);
    }

    // =========================================================================
    // US10 - Consulta de Histórico de Aluguéis
    // =========================================================================

    public synchronized String getHistoricoAlugueis(String usuarioId) throws Exception {
        return getHistoricoAlugueis(usuarioId, "quantidade");
    }

    public synchronized String getHistoricoAlugueis(String usuarioId, String atributo) throws Exception {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IdentificacaoUsuarioNaoPodeSerNulaException();
        }
        Usuario usuario = usuarios.get(usuarioId.trim());
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoNaoExisteException();
        }

        switch (atributo) {
            case "quantidade": {
                int count = 0;
                for (Aluguel a : alugueis.values()) {
                    if (a.getUsuarioId().equals(usuario.getId())) {
                        count++;
                    }
                }
                return String.valueOf(count);
            }
            case "totalGasto": {
                double total = 0.0;
                for (Aluguel a : alugueis.values()) {
                    if (a.getUsuarioId().equals(usuario.getId()) && a.getValorCobrado() != null) {
                        total += a.getValorCobrado();
                    }
                }
                return String.format(Locale.GERMAN, "%.2f", total);
            }
            default:
                throw new AtributoNaoExisteException();
        }
    }

    // =========================================================================
    // US12 - Criar Plano
    // =========================================================================

    public synchronized String criarPlano(String nome, String franquiaMinutos, String valorMinutoExcedente, String mensalidade) throws Exception {
        return executarComandoModificador(() -> {
            Plano plano = ValidadorPlano.validarECriarPlano(nome, franquiaMinutos, valorMinutoExcedente, mensalidade, planos);
            planos.put(plano.getNome(), plano);
            return plano.getNome();
        });
    }

    // =========================================================================
    // US13 - Lançamento de Penalidade
    // =========================================================================

    public synchronized void lancaPenalidade(String usuarioId, String tipo, String valorStr, String motivo) throws Exception {
        executarComandoModificador(() -> {
            if (usuarioId == null || usuarioId.trim().isEmpty()) {
                throw new IdentificacaoUsuarioNaoPodeSerNulaException();
            }
            Usuario usuario = usuarios.get(usuarioId.trim());
            if (usuario == null) {
                throw new UsuarioNaoExisteException();
            }

            if (tipo == null || (!"dano".equals(tipo.trim()) && !"usoIndevido".equals(tipo.trim()))) {
                throw new TipoDePenalidadeInvalidoException();
            }

            if (valorStr == null || valorStr.trim().isEmpty()) {
                throw new ValorNaoPodeSerNuloException();
            }
            double valor;
            try {
                valor = Double.parseDouble(valorStr.trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                throw new ValorDeveSerNumericoException();
            }
            if (valor <= 0) {
                throw new ValorDeveSerPositivoException();
            }

            if (motivo == null || motivo.trim().isEmpty()) {
                throw new MotivoNaoPodeSerNuloException();
            }

            Penalidade penalidade;
            if ("dano".equals(tipo.trim())) {
                penalidade = new PenalidadeDano(valor, motivo.trim());
            } else {
                penalidade = new PenalidadeUsoIndevido(valor, motivo.trim());
            }

            usuario.adicionarPenalidade(penalidade);
        });
    }

    // =========================================================================
    // US14 - Manutenção de Bicicletas
    // =========================================================================

    public synchronized void lancaDefeito(String bicicletaId, String descricao) throws Exception {
        executarComandoModificador(() -> {
            if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
                throw new IdentificacaoBicicletaNaoPodeSerNulaException();
            }
            Bicicleta bike = bicicletas.get(bicicletaId.trim());
            if (bike == null) {
                throw new BicicletaNaoExisteException();
            }

            if (descricao == null || descricao.trim().isEmpty()) {
                throw new DescricaoNaoPodeSerNulaException();
            }

            if ("emUso".equals(bike.getEstado())) {
                throw new NaoEPossivelLancarDefeitoEmBicicletaEmUsoException();
            }

            if ("emManutencao".equals(bike.getEstado())) {
                throw new BicicletaJaEstaEmManutencaoException();
            }

            bike.setEstado("emManutencao");
        });
    }

    public synchronized void concluiManutencao(String bicicletaId) throws Exception {
        executarComandoModificador(() -> {
            if (bicicletaId == null || bicicletaId.trim().isEmpty()) {
                throw new IdentificacaoBicicletaNaoPodeSerNulaException();
            }
            Bicicleta bike = bicicletas.get(bicicletaId.trim());
            if (bike == null) {
                throw new BicicletaNaoExisteException();
            }

            if (!"emManutencao".equals(bike.getEstado())) {
                throw new BicicletaNaoEstaEmManutencaoException();
            }

            bike.setEstado("disponivel");
        });
    }

    // =========================================================================
    // US15 - Relatórios
    // =========================================================================

    public synchronized String getTotalFaturado(String dataInicial, String dataFinal) throws Exception {
        java.time.LocalDate dtIni = ValidadorData.validarEConverterDataApenas(dataInicial);
        java.time.LocalDate dtFim = ValidadorData.validarEConverterDataApenas(dataFinal);

        if (dtFim.isBefore(dtIni)) {
            throw new DataFinalAnteriorADataInicialException();
        }

        double total = 0.0;
        for (Aluguel aluguel : alugueis.values()) {
            if (aluguel.getDataFim() != null && aluguel.getValorCobrado() != null) {
                java.time.LocalDate dataDevolucao = aluguel.getDataFim().toLocalDate();
                if (!dataDevolucao.isBefore(dtIni) && !dataDevolucao.isAfter(dtFim)) {
                    total += aluguel.getValorCobrado();
                }
            }
        }
        return String.format(Locale.GERMAN, "%.2f", total);
    }

    private static final String ARQUIVO_DADOS = "bikeshare_data.ser";

    // =========================================================================
    // Persistência
    // =========================================================================

    public static void salvar(Sistema sistema) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(sistema);
        }
    }

    public static Sistema carregar() {
        File file = new File(ARQUIVO_DADOS);
        if (!file.exists()) {
            return new Sistema();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Sistema sistema = (Sistema) ois.readObject();
            sistema.checkTransients();
            return sistema;
        } catch (Exception e) {
            return new Sistema();
        }
    }
}
