package xplorer.br.com.apiidwall.presenter.callbacks;

import java.util.List;

import xplorer.br.com.apiidwall.presenter.response.ResponseMessage;

public interface CallbackRequest<T> {
    void onSuccess(T data);
    void onSuccess(List<T> data);
    void onFailure(ResponseMessage responseMessage);
}
