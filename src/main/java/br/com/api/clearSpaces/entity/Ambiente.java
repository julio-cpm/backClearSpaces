package br.com.api.clearSpaces.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ambiente")
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String localizacao;

    @Enumerated(EnumType.STRING)
    private TipoEnum tipo;

    @Enumerated(EnumType.STRING)
    private StatusAmbienteEnum statusAmbiente;

    @Column(nullable = false)
    private boolean ocupado = false;

    @ManyToOne
    @JoinColumn(name = "professor_ocupante_id")
    private Professor professorOcupante;

    public enum TipoEnum{
        sala,banheiro,refeitorio
    }

    public enum StatusAmbienteEnum{
        limpo,sujo,pendente
    }

    protected Ambiente(){}

    public Ambiente(String nome, TipoEnum tipo, String localizacao, StatusAmbienteEnum statusAmbiente){
        this.nome = nome;
        this.tipo = tipo;
        this.localizacao = localizacao;
        this.statusAmbiente = statusAmbiente;
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

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public Professor getProfessorOcupante() {
        return professorOcupante;
    }

    public void setProfessorOcupante(Professor professorOcupante) {
        this.professorOcupante = professorOcupante;
    }
}