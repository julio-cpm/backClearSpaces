package br.com.api.clearSpaces.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencia")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Funcionario remetente;

    @Enumerated(EnumType.STRING)
    private GravidadeEnum gravidade;

    @Lob
    @Column(name = "mensagem", columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "encarregado_id")
    private Funcionario encarregado;

    public enum GravidadeEnum {
        leve, moderada, urgente
    }

    public enum StatusEnum {
        pendente, resolvido
    }

    protected Ocorrencia() {}

    public Ocorrencia(Funcionario remetente, GravidadeEnum gravidade, String mensagem) {
        this.remetente = remetente;
        this.gravidade = gravidade;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
        this.status = StatusEnum.pendente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getRemetente() {
        return remetente;
    }

    public void setRemetente(Funcionario remetente) {
        this.remetente = remetente;
    }

    public GravidadeEnum getGravidade() {
        return gravidade;
    }

    public void setGravidade(GravidadeEnum gravidade) {
        this.gravidade = gravidade;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Funcionario getEncarregado() {
        return encarregado;
    }

    public void setEncarregado(Funcionario encarregado) {
        this.encarregado = encarregado;
    }
}