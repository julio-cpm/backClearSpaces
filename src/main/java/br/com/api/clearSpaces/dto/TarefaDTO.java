package br.com.api.clearSpaces.dto;

import br.com.api.clearSpaces.entity.Tarefa.TipoLimpezaEnum;

import java.time.LocalDateTime;

public class TarefaDTO {
    private String descricao;
    private TipoLimpezaEnum tipoLimpeza;
    private Long periodoId;
    private LocalDateTime horarioConclusao;
    private byte[] foto_antes;
    private byte[] foto_depois;

    public TarefaDTO (){}


    public TarefaDTO(String descricao, TipoLimpezaEnum tipoLimpeza, Long periodoId, LocalDateTime horarioConclusao) {
        this.descricao = descricao;
        this.tipoLimpeza = tipoLimpeza;
        this.periodoId = periodoId;
        this.horarioConclusao = horarioConclusao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoLimpezaEnum getTipoLimpeza() {
        return tipoLimpeza;
    }

    public void setTipoLimpeza(TipoLimpezaEnum tipoLimpeza) {
        this.tipoLimpeza = tipoLimpeza;
    }

    public Long getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Long periodoId) {
        this.periodoId = periodoId;
    }

    public LocalDateTime getHorarioConclusao() {
        return horarioConclusao;
    }

    public void setHorarioConclusao(LocalDateTime horarioConclusao) {
        this.horarioConclusao = horarioConclusao;
    }

    public byte[] getFoto_antes() {
        return foto_antes;
    }

    public void setFoto_antes(byte[] foto_antes) {
        this.foto_antes = foto_antes;
    }

    public byte[] getFoto_depois() {
        return foto_depois;
    }

    public void setFoto_depois(byte[] foto_depois) {
        this.foto_depois = foto_depois;
    }
}
