package com.rcbg.afku.investmentdiary.common.responses;

import java.util.Collections;
import java.util.List;

public class BaseApiErrorResponse extends BaseApiResponse{

    private List<String> messages;

    public BaseApiErrorResponse() {}

    public BaseApiErrorResponse(int code, String uri, String message) {
        super(code, uri, "error");
        this.messages = Collections.singletonList(message);
    }

    public BaseApiErrorResponse(int code, String uri, List<String>  messages) {
        super(code, uri, "error");
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> message) {
        this.messages = message;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

}
