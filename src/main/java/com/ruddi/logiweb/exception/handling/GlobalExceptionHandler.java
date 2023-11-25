package com.ruddi.logiweb.exception.handling;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String exception(Model model, Exception exception) {
        model.addAttribute("message", exception.fillInStackTrace().getMessage());
        return "errors/errorpage";
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public String notfound404(Model model, Exception exception) {
        model.addAttribute("message", "Something went wrong :(");
        return "errors/errorpage";
    }
}
