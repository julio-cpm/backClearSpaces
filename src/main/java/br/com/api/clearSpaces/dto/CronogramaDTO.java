package br.com.api.clearSpaces.dto;

import br.com.api.clearSpaces.entity.Cronograma.DiaSemanaEnum;
import java.time.LocalTime;

public class CronogramaDTO {

    private Long funcionarioId;
    private Long ambienteId;
    private Long tarefaId;

    private DiaSemanaEnum diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    public CronogramaDTO() {}

    public CronogramaDTO(Long funcionarioId, Long ambienteId, Long tarefaId, DiaSemanaEnum diaSemana, LocalTime horarioInicio, LocalTime horarioFim) {
        this.funcionarioId = funcionarioId;
        this.ambienteId = ambienteId;
        this.tarefaId = tarefaId;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Long getAmbienteId() {
        return ambienteId;
    }

    public void setAmbienteId(Long ambienteId) {
        this.ambienteId = ambienteId;
    }

    public Long getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Long tarefaId) {
        this.tarefaId = tarefaId;
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
}