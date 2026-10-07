package br.com.api.clearSpaces.dto;

public class OcorrenciaDTO {

    private Long funcionarioId;
    private String gravidade;
    private String mensagem;
    private Long encarregadoId;



    public OcorrenciaDTO() {}

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getEncarregadoId() {
        return encarregadoId;
    }

    public void setEncarregadoId(Long encarregadoId) {
        this.encarregadoId = encarregadoId;
    }
}