package com.acemen.android.wasterz.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Audrik ! on 24/04/2017.
 */

public class UsecaseServiceExecutor implements UsecaseExecutor {
    private ExecutorService mExecutor;
    private static final int MAX_POOL_SIZE = 4;

    public UsecaseServiceExecutor() {
        mExecutor = Executors.newFixedThreadPool(MAX_POOL_SIZE);
    }

    @Override
    public void execute(Runnable runnable) {
        mExecutor.submit(runnable);
    }
}
