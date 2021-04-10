package org.tiny.pool.core.exception;

public class TpInstanceNotCreateException extends Exception{

    public TpInstanceNotCreateException(String error, Exception e) {
        super(error, e);
    }

    public TpInstanceNotCreateException(String error) {
        new TpInstanceNotCreateException(error, null);
    }

}
