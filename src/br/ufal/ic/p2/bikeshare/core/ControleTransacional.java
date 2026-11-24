package br.ufal.ic.p2.bikeshare.core;

import br.ufal.ic.p2.bikeshare.exceptions.NaoHaComandoADesfazerException;
import br.ufal.ic.p2.bikeshare.exceptions.NaoHaComandoARefazerException;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ControleTransacional {
    private Stack<byte[]> undoStack = new Stack<>();
    private Stack<byte[]> redoStack = new Stack<>();

    public synchronized void inicializar() {
        if (undoStack == null) {
            undoStack = new Stack<>();
        }
        if (redoStack == null) {
            redoStack = new Stack<>();
        }
    }

    public synchronized void resetar() {
        if (undoStack != null) {
            undoStack.clear();
        }
        if (redoStack != null) {
            redoStack.clear();
        }
    }

    public synchronized <T> T executar(OperacaoTransacional<T> comando,
                                       Supplier<byte[]> capturadorEstado,
                                       Consumer<byte[]> restauradorEstado) throws Exception {
        inicializar();
        byte[] estadoAnterior = capturadorEstado.get();
        try {
            T resultado = comando.executar();
            undoStack.push(estadoAnterior);
            redoStack.clear();
            return resultado;
        } catch (Exception excecao) {
            restauradorEstado.accept(estadoAnterior);
            throw excecao;
        }
    }

    public synchronized void undo(Supplier<byte[]> capturadorEstado,
                                  Consumer<byte[]> restauradorEstado) throws Exception {
        inicializar();
        if (undoStack.isEmpty()) {
            throw new NaoHaComandoADesfazerException();
        }
        byte[] estadoAtual = capturadorEstado.get();
        byte[] estadoAnterior = undoStack.pop();
        redoStack.push(estadoAtual);
        restauradorEstado.accept(estadoAnterior);
    }

    public synchronized void redo(Supplier<byte[]> capturadorEstado,
                                  Consumer<byte[]> restauradorEstado) throws Exception {
        inicializar();
        if (redoStack.isEmpty()) {
            throw new NaoHaComandoARefazerException();
        }
        byte[] estadoAtual = capturadorEstado.get();
        byte[] estadoProximo = redoStack.pop();
        undoStack.push(estadoAtual);
        restauradorEstado.accept(estadoProximo);
    }
}
