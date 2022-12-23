package com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects;

import com.rcbg.afku.investmentdiary.marketoperations.entities.OperationDescription;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;


public class MarketOperationDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Date creationDate;
    @Past(message = "You cannot create operation from feature")
    @NotNull(message = "'operationDate' field is required")
    Date operationDate;
    @NotNull(message = "'pricePerOne' field is required")
    @Positive(message = "Price cannot be negative number or zero")
    BigDecimal pricePerOne;
    @NotNull(message = "'volume' field is required")
    @Positive(message = "Volume cannot be negative number or zero")
    Integer volume;
    @NotBlank(message = "'operationType' field is required")
    String operationType;
    OperationDescription description;
    @NotNull(message = "'subjectId' field is required")
    @Positive(message = "Subjects id is always positive integer")
    Integer subjectId;
    @NotNull(message = "'accountId' field is required")
    @Positive(message = "Account id is always positive integer")
    Integer accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getPricePerOne() {
        return pricePerOne;
    }

    public void setPricePerOne(BigDecimal pricePerOne) {
        this.pricePerOne = pricePerOne;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public OperationDescription getDescription() {
        return description;
    }

    public void setDescription(OperationDescription description) {
        this.description = description;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public MarketOperationDTO(){}

    @Override
    public String toString(){
        return "{" + "id: " + this.id +
                ", creationDate: " + this.creationDate +
                ", transactionDate: " + this.operationDate +
                ", price: " + this.pricePerOne +
                ", volume: " + this.volume +
                ", operationType: " + this.operationType +
                ", subjectId: " + this.subjectId +
                ", accountId: " + this.accountId +
                "}";
    }
}
