package br.com.api.clearSpaces.service;

import br.com.api.clearSpaces.entity.Notificacao;
import br.com.api.clearSpaces.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificacaoService {

    private static final long SSE_TIMEOUT = 30L * 60 * 1000;

    private final NotificacaoRepository repository;
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public SseEmitter conectar(Long funcionarioId) {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

        emitter.onCompletion(() -> emitters.remove(funcionarioId, emitter));
        emitter.onTimeout(() -> emitters.remove(funcionarioId, emitter));
        emitter.onError(e -> emitters.remove(funcionarioId, emitter));

        emitters.put(funcionarioId, emitter);
        return emitter;
    }

    public void notificarFuncionario(Long funcionarioId, String mensagem) {
        notificarFuncionario(funcionarioId, "🧹 Sala Liberada", mensagem, "info");
    }

    public void notificarFuncionario(Long funcionarioId, String titulo, String mensagem, String tipo) {
        Notificacao notificacao = new Notificacao(funcionarioId, titulo, mensagem, tipo);
        repository.save(notificacao);

        SseEmitter emitter = emitters.get(funcionarioId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("ambiente-liberado")
                        .data(mensagem));
            } catch (IOException e) {
                emitters.remove(funcionarioId, emitter);
            }
        }
    }

    public List<Notificacao> listarPorFuncionario(Long funcionarioId) {
        return repository.findByFuncionarioIdOrderByDataHoraDesc(funcionarioId);
    }

    public void marcarComoLida(Long id) {
        repository.findById(id).ifPresent(n -> {
            n.setLida(true);
            repository.save(n);
        });
    }

    public void marcarTodasComoLidas(Long funcionarioId) {
        List<Notificacao> lista = repository.findByFuncionarioIdOrderByDataHoraDesc(funcionarioId);
        lista.forEach(n -> n.setLida(true));
        repository.saveAll(lista);
    }

    public void limpar(Long funcionarioId) {
        List<Notificacao> lista = repository.findByFuncionarioIdOrderByDataHoraDesc(funcionarioId);
        repository.deleteAll(lista);
    }
}