package com.acemen.android.wasterz.executor;

import android.os.Handler;
import android.os.Looper;

import com.acemen.android.wasterz.domain.AbstractUsecase;

public class UsecaseHandler {
    private static UsecaseHandler INSTANCE;
    private final UsecaseExecutor mUsecaseExecutor;
    private final Handler mHandler;

    private UsecaseHandler(UsecaseExecutor usecaseExecutor) {
        mUsecaseExecutor = usecaseExecutor;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public <R extends AbstractUsecase.Request, Q extends AbstractUsecase.Response> void executeUsecase(
            final AbstractUsecase<R, Q> usecase,
            final R request,
            final AbstractUsecase.UsecaseCallback<Q> callback) {

        //init usecase param
        usecase.setUsecaseCallback(new UiCallbackWrapper<Q>(this, callback));
        mUsecaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                usecase.run(request);
            }
        });
    }

    public <V extends AbstractUsecase.Response> void notifyResponse(final V response, final
    AbstractUsecase.UsecaseCallback<V> callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response);
            }
        });
    }

    public <V extends AbstractUsecase.Response> void notifyError(final
    AbstractUsecase.UsecaseCallback<V> callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }

    private final class UiCallbackWrapper<V extends AbstractUsecase.Response> implements
            AbstractUsecase.UsecaseCallback<V> {
        private final UsecaseHandler mUsecaseHandler;
        private final AbstractUsecase.UsecaseCallback<V> mUsecaseCallback;

        public UiCallbackWrapper(UsecaseHandler usecaseHandler,
                AbstractUsecase.UsecaseCallback<V> usecaseCallback) {
            mUsecaseHandler = usecaseHandler;
            mUsecaseCallback = usecaseCallback;
        }

        @Override
        public void onSuccess(V response) {
            mUsecaseHandler.notifyResponse(response, mUsecaseCallback);
        }

        @Override
        public void onError() {
            mUsecaseHandler.notifyError(mUsecaseCallback);
        }
    }

    public static UsecaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsecaseHandler(new UsecaseServiceExecutor());
        }
        return INSTANCE;
    }
}
