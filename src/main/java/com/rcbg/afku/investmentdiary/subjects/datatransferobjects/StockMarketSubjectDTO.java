package com.rcbg.afku.investmentdiary.subjects.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.BaseValidationGroup;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StockMarketSubjectDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    int id;

    @NotBlank(message = "'name' field is required", groups = OnCreate.class)
    @Size(min = 3, max = 32, message = "'name' field should be between 3 and 32 character long", groups = BaseValidationGroup.class)
    String name;

    @Size(max = 255, message = "'infoSources' field can be max 255 characters long", groups = BaseValidationGroup.class)
    String infoSources;

    @NotNull(message = "'hasDividend' field is required", groups = OnCreate.class)
    Boolean hasDividend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean isHasDividend() {
        return hasDividend;
    }

    public void setHasDividend(Boolean hasDividend) {
        this.hasDividend = hasDividend;
    }

    public StockMarketSubjectDTO(){}

    @Override
    public String toString(){
        return "{" + "id: " + this.id +
                ", name: " + this.name +
                ", infoSources: " + this.infoSources +
                ", hasDividend: " + this.hasDividend +
                "}";
    }
}
