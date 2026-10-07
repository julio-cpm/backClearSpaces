package br.com.api.clearSpaces.controller;

import br.com.api.clearSpaces.entity.Notificacao;
import br.com.api.clearSpaces.service.NotificacaoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@CrossOrigin("*")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping(value = "/stream/{funcionarioId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter inscrever(@PathVariable Long funcionarioId) {
        return notificacaoService.conectar(funcionarioId);
    }

    @GetMapping("/usuario/{funcionarioId}")
    public List<Notificacao> listar(@PathVariable Long funcionarioId) {
        return notificacaoService.listarPorFuncionario(funcionarioId);
    }

    @PutMapping("/{id}/lida")
    public ResponseEntity<?> marcarComoLida(@PathVariable Long id) {
        notificacaoService.marcarComoLida(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ler/{funcionarioId}")
    public ResponseEntity<?> marcarTodasComoLidas(@PathVariable Long funcionarioId) {
        notificacaoService.marcarTodasComoLidas(funcionarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/limpar/{funcionarioId}")
    public ResponseEntity<?> limparNotificacao(@PathVariable Long funcionarioId){
        notificacaoService.limpar(funcionarioId);
        return ResponseEntity.ok().build();
    }
}