package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class BrokerAccountDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    int id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Date creationDate;

    String provider;

    String accountId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
