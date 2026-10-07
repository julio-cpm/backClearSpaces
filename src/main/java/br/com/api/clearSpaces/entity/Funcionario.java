package br.com.api.clearSpaces.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionario")

public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String matricula;
    @Column(unique = true, nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private FuncaoEnum funcao;

    @ManyToOne
    @JoinColumn(name = "periodo_id")
    private Periodo periodo;

    public enum FuncaoEnum{
        banheirista, asg, gerente
    }

    protected Funcionario(){}

    public Funcionario(String nome, String cpf, String senha, FuncaoEnum funcao, Periodo periodo){
        Long randomLong = Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextLong());

        this.nome = nome;

        Long limiteMatricula = randomLong % 10000;

        this.matricula = "FUNC-" + String.format("%04d", limiteMatricula);
        this.cpf = cpf;
        this.senha = senha;
        this.funcao = funcao;
        this.periodo = periodo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public FuncaoEnum getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoEnum funcao) {
        this.funcao = funcao;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
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
