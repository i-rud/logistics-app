package com.ruddi.logiweb.exception.exceptions;

public class NullSourceException extends Throwable{
    public NullSourceException() {
        super("You cannot apoint nothing to an order. Select at least one entity.");
    }
}
