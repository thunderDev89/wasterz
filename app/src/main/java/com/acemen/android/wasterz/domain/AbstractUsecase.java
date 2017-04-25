package com.acemen.android.wasterz.domain;

/**
 * Created by Audrik ! on 08/04/2017.
 */

public abstract class AbstractUsecase<Q extends AbstractUsecase.Request, R extends AbstractUsecase.Response> {
    private UsecaseCallback<R> mUsecaseCallback;


    public abstract void run(Q request);

    public interface Request {}

    public interface Response {}

    public UsecaseCallback<R> getUsecaseCallback() {
        return mUsecaseCallback;
    }

    public void setUsecaseCallback(
            UsecaseCallback<R> usecaseCallback) {
        mUsecaseCallback = usecaseCallback;
    }

    public interface UsecaseCallback<R> {
        void onSuccess(R response);

        void onError();
    }
}
