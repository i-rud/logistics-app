package com.ruddi.logiweb.exception.exceptions;

public class EntityEditException extends Throwable{
    public EntityEditException() {
        super("Record can not be edited because it related to in-progress order.");
    }
}
