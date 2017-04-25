package com.acemen.android.wasterz.domain.contribute.usecase;

import android.support.annotation.NonNull;

import com.acemen.android.wasterz.domain.AbstractUsecase;
import com.acemen.android.wasterz.domain.WasteDataSource;
import com.acemen.android.wasterz.domain.model.Waste;

import timber.log.Timber;

/**
 * Created by Audrik ! on 07/04/2017.
 */

public class AddWaste extends AbstractUsecase<AddWaste.Request, AddWaste.Response> {

    private final WasteDataSource mRepo;

    public AddWaste(WasteDataSource dataSource) {
        mRepo = dataSource;
    }

    @Override
    public void run(Request request) {
        mRepo.addWaste(request.getWaste(), new WasteDataSource.AddWasteCallback() {
            @Override
            public void onWasteAdded(String msg) {
                Timber.d("Waste added");
                getUsecaseCallback().onSuccess(new Response());
            }

            @Override
            public void onDataNotAvailable() {
                getUsecaseCallback().onError();
            }
        });
    }

    public static final class Request implements AbstractUsecase.Request {
        private final Waste mWaste;

        public Request(@NonNull Waste mWaste) {
            this.mWaste = mWaste;
        }

        public Waste getWaste() {
            return mWaste;
        }
    }
    public static final class Response implements AbstractUsecase.Response{}
}
