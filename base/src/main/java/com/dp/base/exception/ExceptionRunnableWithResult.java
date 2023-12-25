package com.dp.base.exception;

public interface ExceptionRunnableWithResult<T> {
    T run() throws Exception;
}
