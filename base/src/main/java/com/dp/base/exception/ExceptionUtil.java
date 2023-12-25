package com.dp.base.exception;

public class ExceptionUtil {
    public static <T> T ignoreExceptionWithResult(ExceptionRunnableWithResult<T> runnableWithResult, T defaultReturnValue) {
        return ignoreExceptionWithResult(runnableWithResult, defaultReturnValue, true);
    }

    public static <T> T ignoreExceptionWithResult(ExceptionRunnableWithResult<T> runnableWithResult, T defaultReturnValue, boolean printStackTrace) {
        try {
            return runnableWithResult.run();
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
        }
        return defaultReturnValue;
    }

    public static void ignoreException(ExceptionRunnable runnable) {
        ignoreException(runnable, true);
    }

    public static void ignoreException(ExceptionRunnable runnable, boolean printStackTrace) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
        }
    }
}
