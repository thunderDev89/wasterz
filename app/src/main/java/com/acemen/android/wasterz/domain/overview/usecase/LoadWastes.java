package com.acemen.android.wasterz.domain.overview.usecase;

import android.support.annotation.NonNull;

import com.acemen.android.wasterz.domain.AbstractUsecase;
import com.acemen.android.wasterz.domain.WasteDataSource;
import com.acemen.android.wasterz.domain.model.Waste;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Audrik ! on 09/04/2017.
 */

public class LoadWastes extends AbstractUsecase<AbstractUsecase.Request, LoadWastes.Response> {

    private final WasteDataSource mRepo;

    public LoadWastes(WasteDataSource mRepo) {
        this.mRepo = mRepo;
    }

    @Override
    public void execute(AbstractUsecase.Request request, @NonNull final UsecaseCallback<Response> usecaseCallback) {
        mRepo.loadWastes(new WasteDataSource.LoadWastesCallback() {
            @Override
            public void onWastesLoaded(List<Waste> wastes) {
                Timber.d("Wastes sucessfully loaded");
                final Response response = new Response(wastes);
                usecaseCallback.onSuccess(response);
            }

            @Override
            public void onDataNotAvailable() {
                usecaseCallback.onError();
            }
        });
    }

    public static final class Request implements AbstractUsecase.Request {}

    public static final class Response implements AbstractUsecase.Response {
        private final List<Waste> mWastes;

        public Response(List<Waste> mWastes) {
            this.mWastes = mWastes;
        }

        public List<Waste> getWastes() {
            return mWastes;
        }
    }
}
