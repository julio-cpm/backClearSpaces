package br.com.api.clearSpaces.dto;

import br.com.api.clearSpaces.entity.Funcionario.FuncaoEnum;

public class FuncionarioDTO {
    private String nome;
    private String cpf;
    private String senha;
    private String senhaAntiga;
    private FuncaoEnum funcao;
    private Long periodoId;

    public FuncionarioDTO (){}

    public FuncionarioDTO(String nome, String cpf, String senhaAntiga,String senha, FuncaoEnum funcao, Long periodoId){
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.funcao = funcao;
        this.periodoId = periodoId;
        this.senhaAntiga = senhaAntiga;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public FuncaoEnum getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoEnum funcao) {
        this.funcao = funcao;
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

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }
}