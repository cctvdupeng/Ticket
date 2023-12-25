package com.dp.base.utils;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtil {
    private ExecutorService mExecutorService;

    private ExecutorUtil() {
    }

    private static final class InnerHolder {
        private static final ExecutorUtil INSTANCE = new ExecutorUtil();
    }

    private static ExecutorUtil getInstance() {
        return InnerHolder.INSTANCE;
    }

    public static void execute(@NonNull ISchedule ISchedule) {
        getInstance().internalExecute(ISchedule);
    }

    private void internalExecute(@NonNull ISchedule ISchedule) {
        ensureWorkerHandler();
        new Task().executeOnExecutor(mExecutorService, ISchedule);
    }

    private void ensureWorkerHandler() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool();
        }
    }

    private final static class Task extends AsyncTask<ISchedule, Void, ISchedule> {

        @Override
        protected ISchedule doInBackground(ISchedule... params) {
            if (params == null || params.length == 0) {
                return null;
            }
            ISchedule ISchedule = params[0];
            ISchedule.runWorker();
            return ISchedule;
        }

        @Override
        protected void onPostExecute(ISchedule ISchedule) {
            if (ISchedule != null) {
                ISchedule.runUI();
            }
        }
    }

    public interface ISchedule {

        /**
         * 后台线程
         */
        void runWorker();

        /**
         * UI线程
         */
        void runUI();
    }
}
