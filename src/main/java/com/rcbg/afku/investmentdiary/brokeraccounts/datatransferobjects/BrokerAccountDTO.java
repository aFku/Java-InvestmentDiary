package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.common.utils.validationgroups.BaseValidationGroup;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class BrokerAccountDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    int id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Date creationDate;

    @NotBlank(message = "'provider' field is required", groups = OnCreate.class)
    @Size(min = 3, max = 32, message = "'provider' field should be between 3 and 32 character long", groups = BaseValidationGroup.class)
    String provider;

    @NotBlank(message = "'accountId' field is required", groups = OnCreate.class)
    @Size(min = 1, max = 32, message = "'accountId' field should be between 1 and 32 character long", groups = BaseValidationGroup.class)
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
