package com.rcbg.afku.investmentdiary.subjects.datatransferobjects;

import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;

public class StockMarketSubjectDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    int id;

    @NotEmpty
    String name;

    String infoSources;

    boolean hasDividend;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfoSources() {
        return infoSources;
    }

    public void setInfoSources(String infoSources) {
        this.infoSources = infoSources;
    }

    public boolean isHasDividend() {
        return hasDividend;
    }

    public void setHasDividend(boolean hasDividend) {
        this.hasDividend = hasDividend;
    }

    public StockMarketSubjectDTO(){}

    public StockMarketSubjectDTO(StockMarketSubject subject){
        this.id = subject.getId();
        this.name = subject.getName();
        this.infoSources = subject.getInfoSources();
        this.hasDividend = subject.hasDividend();
    }
}
