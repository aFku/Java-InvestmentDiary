package com.rcbg.afku.investmentdiary.common.statuses;

public class ResourceDeletedStatus <T> {

    private T id;
    private boolean deleted;
    private String kind;

    public ResourceDeletedStatus() {}

    public ResourceDeletedStatus(T id, boolean deleted, String kind) {
        this.id = id;
        this.deleted = deleted;
        this.kind = kind;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
