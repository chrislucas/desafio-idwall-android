package xplorer.br.com.apiidwall.model;

import xplorer.br.com.apiidwall.presenter.response.ResponseMessage;

public class ErrorMessage implements ResponseMessage {
    private String message;
    public ErrorMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
