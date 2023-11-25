package com.ruddi.logiweb.exception.handling;

import com.ruddi.logiweb.exception.exceptions.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final String REDIRECT_URL = "errors/errorpage";
    private static final String MODEL_ATTRIBUTE_NAME = "message";

    @ExceptionHandler(value = EntityDeleteException.class)
    public String entityDeleteExceptionHandling
            (Model model, EntityDeleteException entityDeleteException) {
        model.addAttribute(MODEL_ATTRIBUTE_NAME, entityDeleteException.getMessage());
        return REDIRECT_URL;
    }

    @ExceptionHandler(value = NotEnoughDriversException.class)
    public String notEnoughDriversExceptionHangling
            (Model model, NotEnoughDriversException notEnoughDriversException) {
        model.addAttribute(MODEL_ATTRIBUTE_NAME, notEnoughDriversException.getMessage());
        return REDIRECT_URL;
    }

    @ExceptionHandler(value = EntityEditException.class)
    public String entityEditException
            (Model model, EntityEditException entityEditException) {
        model.addAttribute(MODEL_ATTRIBUTE_NAME, entityEditException.getMessage());
        return REDIRECT_URL;
    }

    @ExceptionHandler(value = NullSourceException.class)
    public String nullSourceException
            (Model model, NullSourceException nullSourceException) {
        model.addAttribute(MODEL_ATTRIBUTE_NAME, nullSourceException.getMessage());
        return REDIRECT_URL;
    }
}
