package com.rcbg.afku.investmentdiary.brokeraccounts.responses;

public class IdResponse extends SimpleResponse{

    private int id;

    public IdResponse(int code, String message, int id){
        super(code, message);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
