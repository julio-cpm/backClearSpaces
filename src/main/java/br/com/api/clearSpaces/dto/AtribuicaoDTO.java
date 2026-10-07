package br.com.api.clearSpaces.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AtribuicaoDTO {
    private Long funcionarioId;
    private Long tarefaId;
    private Long ambienteId;
    private LocalDate dia;
    private String diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    public AtribuicaoDTO (){}

    public AtribuicaoDTO(Long funcionarioId, Long tarefaId, Long ambienteId, LocalDate dia,
                         String diaSemana, LocalTime horarioInicio, LocalTime horarioFim) {
        this.funcionarioId = funcionarioId;
        this.tarefaId = tarefaId;
        this.ambienteId = ambienteId;
        this.dia = dia;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
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

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
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