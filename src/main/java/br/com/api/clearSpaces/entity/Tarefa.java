package br.com.api.clearSpaces.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoLimpezaEnum tipoLimpezaEnum;

    @ManyToOne
    @JoinColumn(name = "periodo_id")
    private Periodo periodo;

    @Column(name = "horario_conclusao")
    private LocalDateTime horarioConclusao;

    @JsonIgnore
    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atribuicao> atribuicoes = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Procedimento> procedimentos = new ArrayList<>();

    public enum TipoLimpezaEnum {
        intensiva, manutencao, moderada
    }

    @Lob
    @Column(name = "foto_antes", columnDefinition = "LONGBLOB")
    private byte[] foto_antes;

    @Lob
    @Column(name = "foto_depois", columnDefinition = "LONGBLOB")
    private byte[] foto_depois;

    protected Tarefa() {}

    public Tarefa(String descricao, TipoLimpezaEnum tipoLimpeza, Periodo periodo, LocalDateTime horarioConclusao) {
        this.descricao = descricao;
        this.tipoLimpezaEnum = tipoLimpeza;
        this.periodo = periodo;
        this.horarioConclusao = horarioConclusao;
    }

    // --- Métodos de Regra de Negócio ---

    /**
     * Verifica se todos os procedimentos vinculados foram marcados como concluídos.
     */
    public boolean todosProcedimentosConcluidos() {
        if (procedimentos.isEmpty()) {
            return true; // Se não houver procedimentos cadastrados, considera liberado
        }
        return procedimentos.stream().allMatch(Procedimento::isConcluido);
    }

    /**
     * Tenta finalizar a tarefa registrando o horário atual apenas se todos os procedimentos estiverem concluídos.
     */
    public boolean concluirTarefa() {
        if (todosProcedimentosConcluidos()) {
            this.horarioConclusao = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public void adicionarProcedimento(Procedimento procedimento) {
        procedimentos.add(procedimento);
        procedimento.setTarefa(this);
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public TipoLimpezaEnum getTipoLimpezaEnum() { return tipoLimpezaEnum; }
    public void setTipoLimpezaEnum(TipoLimpezaEnum tipoLimpezaEnum) { this.tipoLimpezaEnum = tipoLimpezaEnum; }

    public Periodo getPeriodo() { return periodo; }
    public void setPeriodo(Periodo periodo) { this.periodo = periodo; }

    public LocalDateTime getHorarioConclusao() { return horarioConclusao; }
    public void setHorarioConclusao(LocalDateTime horarioConclusao) { this.horarioConclusao = horarioConclusao; }

    public List<Atribuicao> getAtribuicoes() { return atribuicoes; }
    public void setAtribuicoes(List<Atribuicao> atribuicoes) { this.atribuicoes = atribuicoes; }

    public List<Procedimento> getProcedimentos() { return procedimentos; }
    public void setProcedimentos(List<Procedimento> procedimentos) { this.procedimentos = procedimentos; }

    public byte[] getFoto_antes() { return foto_antes; }
    public void setFoto_antes(byte[] foto_antes) { this.foto_antes = foto_antes; }

    public byte[] getFoto_depois() { return foto_depois; }
    public void setFoto_depois(byte[] foto_depois) { this.foto_depois = foto_depois; }
}
