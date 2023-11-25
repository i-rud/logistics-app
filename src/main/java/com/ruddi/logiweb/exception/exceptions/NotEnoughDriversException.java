package com.ruddi.logiweb.exception.exceptions;

public class NotEnoughDriversException extends Throwable{
    public NotEnoughDriversException() {
        super("Count of choosen drivers is less than required count of drivers." +
                " You cannot start the order :(");
    }
}
