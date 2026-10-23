package com.clearspaces.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String tipoLimpeza;

    @ManyToOne
    @JoinColumn(name = "periodo_id")
    private Periodo periodo;

    private String horarioConclusao;
    private String fotoAntes;
    private String fotoDepois;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Procedimento> procedimentos = new ArrayList<>();

    public Tarefa() {}

    public void adicionarProcedimento(String desc) {
        Procedimento proc = new Procedimento(desc, this);
        this.procedimentos.add(proc);
    }

    public void limparProcedimentos() {
        this.procedimentos.clear();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipoLimpeza() { return tipoLimpeza; }
    public void setTipoLimpeza(String tipoLimpeza) { this.tipoLimpeza = tipoLimpeza; }

    public Periodo getPeriodo() { return periodo; }
    public void setPeriodo(Periodo periodo) { this.periodo = periodo; }

    public String getHorarioConclusao() { return horarioConclusao; }
    public void setHorarioConclusao(String horarioConclusao) { this.horarioConclusao = horarioConclusao; }

    public String getFotoAntes() { return fotoAntes; }
    public void setFotoAntes(String fotoAntes) { this.fotoAntes = fotoAntes; }

    public String getFotoDepois() { return fotoDepois; }
    public void setFotoDepois(String fotoDepois) { this.fotoDepois = fotoDepois; }

    public List<Procedimento> getProcedimentos() { return procedimentos; }
    public void setProcedimentos(List<Procedimento> procedimentos) { this.procedimentos = procedimentos; }
}
