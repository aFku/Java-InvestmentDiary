package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletRecordDTO {
    String name;
    BigDecimal volume;
    Integer accountId;
    Integer subjectId;

    public WalletRecordDTO() {
    }

    public WalletRecordDTO(Object[] data) {
        try {this.volume = (BigDecimal) data[0];} catch (IndexOutOfBoundsException e){this.volume = null;}
        try {this.name = (String) data[1];} catch (IndexOutOfBoundsException e){this.name = null;}
        try {this.subjectId = (Integer) data[2];} catch (IndexOutOfBoundsException e){this.subjectId = null;}
        try {this.accountId = (Integer) data[3];} catch (IndexOutOfBoundsException e){this.accountId = null;}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
}
