package com.ruddi.logiweb.exception.exceptions;

public class EntityDeleteException extends Throwable{
    public EntityDeleteException() {
        super("Record can not be deleted " +
                "because it related to another record as a foreign key:(");
    }
}
