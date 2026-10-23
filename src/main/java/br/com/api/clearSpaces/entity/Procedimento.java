package br.com.api.clearSpaces.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "procedimento")
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private boolean concluido = false;

    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    @JsonBackReference
    private Tarefa tarefa;

    public Procedimento() {}

    public Procedimento(String descricao, Tarefa tarefa) {
        this.descricao = descricao;
        this.tarefa = tarefa;
        this.concluido = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public boolean isConcluido() { return concluido; }
    public void setConcluido(boolean concluido) { this.concluido = concluido; }

    public Tarefa getTarefa() { return tarefa; }
    public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }
}
