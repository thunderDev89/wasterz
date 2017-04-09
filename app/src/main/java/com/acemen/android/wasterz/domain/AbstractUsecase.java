package com.acemen.android.wasterz.domain;

import android.support.annotation.NonNull;

/**
 * Created by Audrik ! on 08/04/2017.
 */

public abstract class AbstractUsecase<Q extends AbstractUsecase.Request, R extends AbstractUsecase.Response> {

    public abstract void execute(Q request, @NonNull UsecaseCallback<R> usecaseCallback);

    public interface Request {}

    public interface Response {}

    public interface UsecaseCallback<R> {
        void onSuccess(R response);

        void onError();
    }
}
