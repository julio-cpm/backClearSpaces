package br.com.api.clearSpaces.dto;

public class ProfessorDTO {

    private String nome;
    private String cpf;
    private String senha;
    private Long periodoId;

    public ProfessorDTO (){}

    public ProfessorDTO(String nome, String cpf, String senha, Long periodoId){
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.periodoId = periodoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Long periodoId) {
        this.periodoId = periodoId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}