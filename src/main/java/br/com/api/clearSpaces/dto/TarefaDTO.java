package br.com.api.clearSpaces.dto;

import java.util.List;

public class TarefaDTO {
    private String descricao;
    private String tipoLimpeza;
    private Long periodoId;
    private String horarioConclusao;
    private List<String> procedimentos;

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipoLimpeza() { return tipoLimpeza; }
    public void setTipoLimpeza(String tipoLimpeza) { this.tipoLimpeza = tipoLimpeza; }

    public Long getPeriodoId() { return periodoId; }
    public void setPeriodoId(Long periodoId) { this.periodoId = periodoId; }

    public String getHorarioConclusao() { return horarioConclusao; }
    public void setHorarioConclusao(String horarioConclusao) { this.horarioConclusao = horarioConclusao; }

    public List<String> getProcedimentos() { return procedimentos; }
    public void setProcedimentos(List<String> procedimentos) { this.procedimentos = procedimentos; }
}
