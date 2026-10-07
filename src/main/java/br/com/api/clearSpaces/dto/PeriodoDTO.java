package br.com.api.clearSpaces.dto;

import br.com.api.clearSpaces.entity.Periodo.PeriodoEnum;

public class PeriodoDTO {

    private PeriodoEnum periodoEnum;

    public PeriodoDTO() {}

    public PeriodoDTO(PeriodoEnum periodoEnum) {
        this.periodoEnum = periodoEnum;
    }

    public PeriodoEnum getPeriodoEnum() {
        return periodoEnum;
    }

    public void setPeriodoEnum(PeriodoEnum periodoEnum) {
        this.periodoEnum = periodoEnum;
    }
}