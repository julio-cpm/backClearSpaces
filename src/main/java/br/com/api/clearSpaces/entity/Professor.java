package br.com.api.clearSpaces.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "professor")

public class Professor {
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

    @ManyToOne
    @JoinColumn(name = "periodo_id")
    private Periodo periodo;

    protected Professor(){}

    public Professor(String nome, String cpf, String senha, Periodo periodo){
        Long randomLong = Math.abs(java.util.concurrent.ThreadLocalRandom.current().nextLong());

        this.nome = nome;

        Long limiteMatricula = randomLong % 10000;

        this.matricula = "PROF-" + String.format("%04d", limiteMatricula);
        this.cpf = cpf;
        this.senha = senha;
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

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
