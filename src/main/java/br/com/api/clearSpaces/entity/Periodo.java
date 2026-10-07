package br.com.api.clearSpaces.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "periodo")
public class Periodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PeriodoEnum periodoEnum;

    public enum PeriodoEnum{
        manha,tarde,noite
    }

    protected Periodo(){}

    public Periodo(PeriodoEnum periodoEnum){
        this.periodoEnum = periodoEnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PeriodoEnum getPeriodoEnum() {
        return periodoEnum;
    }

    public void setPeriodoEnum(PeriodoEnum periodoEnum) {
        this.periodoEnum = periodoEnum;
    }
}