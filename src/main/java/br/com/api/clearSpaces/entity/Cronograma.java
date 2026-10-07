package br.com.api.clearSpaces.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "cronograma")
public class Cronograma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cronograma")
    private Long idCronograma;

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_ambiente")
    private Ambiente ambiente;

    @ManyToOne
    @JoinColumn(name = "id_tarefa")
    private Tarefa tarefa;

    @Enumerated(EnumType.STRING)
    private DiaSemanaEnum diaSemana;

    private LocalTime horarioInicio;

    private LocalTime horarioFim;

    private LocalDateTime horarioConclusao;

    public enum DiaSemanaEnum {
        segunda, terça, quarta, quinta, sexta
    }

    protected Cronograma() {}

    public Cronograma(Funcionario funcionario, Ambiente ambiente, Tarefa tarefa, DiaSemanaEnum diaSemana, LocalTime horarioInicio, LocalTime horarioFim) {
        this.funcionario = funcionario;
        this.ambiente = ambiente;
        this.tarefa = tarefa;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public Long getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(Long idCronograma) {
        this.idCronograma = idCronograma;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public DiaSemanaEnum getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemanaEnum diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public LocalDateTime getHorarioConclusao() {
        return horarioConclusao;
    }

    public void setHorarioConclusao(LocalDateTime horarioConclusao) {
        this.horarioConclusao = horarioConclusao;
    }
}