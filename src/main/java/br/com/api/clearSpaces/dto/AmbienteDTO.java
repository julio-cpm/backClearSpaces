package br.com.api.clearSpaces.dto;

import br.com.api.clearSpaces.entity.Ambiente.StatusAmbienteEnum;
import br.com.api.clearSpaces.entity.Ambiente.TipoEnum;

public class AmbienteDTO {
    private String nome;
    private String localizacao;
    private TipoEnum tipo;
    private StatusAmbienteEnum statusAmbiente;

    public AmbienteDTO (){}

    public AmbienteDTO(String nome, String localizacao, TipoEnum tipo, StatusAmbienteEnum statusAmbiente) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.tipo = tipo;
        this.statusAmbiente = statusAmbiente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public TipoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnum tipo) {
        this.tipo = tipo;
    }

    public StatusAmbienteEnum getStatusAmbiente() {
        return statusAmbiente;
    }

    public void setStatusAmbiente(StatusAmbienteEnum statusAmbiente) {
        this.statusAmbiente = statusAmbiente;
    }
}