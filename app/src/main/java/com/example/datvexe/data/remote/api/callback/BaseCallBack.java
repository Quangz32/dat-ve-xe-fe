package com.example.datvexe.data.remote.api.callback;

import java.util.List;

public interface BaseCallBack<T> {
    void onSuccess(List<T> data);
    void onError(String error);
    void onFailure(Throwable throwable);
}
