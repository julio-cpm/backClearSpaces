package br.com.api.clearSpaces.dto;

public class OcupacaoAmbienteDTO {
    private Long professorId;

    public OcupacaoAmbienteDTO() {}

    public OcupacaoAmbienteDTO(Long professorId) {
        this.professorId = professorId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}