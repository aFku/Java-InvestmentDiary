package com.rcbg.afku.investmentdiary.common.statuses;

public class ResourceDeletedStatus {

    private int id;
    private boolean deleted;
    private String kind;

    public ResourceDeletedStatus() {}

    public ResourceDeletedStatus(int id, boolean deleted, String kind) {
        this.id = id;
        this.deleted = deleted;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
