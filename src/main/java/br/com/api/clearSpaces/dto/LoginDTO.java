package br.com.api.clearSpaces.dto;

public class LoginDTO {
    private String matricula;
    private String cpf;
    private String senha;

    public LoginDTO (){}

    public LoginDTO(String matricula, String cpf, String senha){
        this.matricula = matricula;
        this.cpf = cpf;
        this.senha = senha;
    }



    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
