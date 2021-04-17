package org.tiny.pool.core.exception;

public class TpPoolCreateErrorException extends Exception{

    public TpPoolCreateErrorException(String error, Exception e) {
        super(error, e);
    }

    public TpPoolCreateErrorException(String error) {
        new TpInstanceNotCreateException(error, null);
    }

}
