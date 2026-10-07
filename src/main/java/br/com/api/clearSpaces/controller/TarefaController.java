    package br.com.api.clearSpaces.controller;

    import br.com.api.clearSpaces.dto.TarefaDTO;
    import br.com.api.clearSpaces.entity.Tarefa;
    import br.com.api.clearSpaces.service.TarefaService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/tarefas")
    @CrossOrigin("*")
    public class TarefaController {

        private final TarefaService tarefaService;

        public TarefaController(TarefaService tarefaService) {
            this.tarefaService = tarefaService;
        }

        @PostMapping
        public Tarefa criar(@RequestBody TarefaDTO dto) {
            return tarefaService.salvar(dto);
        }

        @GetMapping
        public List<Tarefa> listar() {
            return tarefaService.listarTodas();
        }

        @PutMapping("/{id}")
        public Tarefa atualizar(@PathVariable Long id, @RequestBody TarefaDTO dto) {
            return tarefaService.atualizar(id, dto);
        }

        @PutMapping("/{id}/ft_antes")
        public Tarefa adicionarFotoAntes(@PathVariable Long id, @RequestBody TarefaDTO dto) {
            return tarefaService.salvarFotoAntes(id, dto);
        }

        @PutMapping("/{id}/ft_depois")
        public Tarefa adicionarFotoDepois(@PathVariable Long id, @RequestBody TarefaDTO dto) {
            return tarefaService.salvarFotoDepois(id, dto);
        }

        @DeleteMapping("/{id}")
        public void deletar(@PathVariable Long id) {
            tarefaService.deletar(id);
        }
    }