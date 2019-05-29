package demo.stockmgr.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller Advice for Exception Handling
 * @author Teki Chan
 * @since 29 May 2019
 */
@ControllerAdvice
public class ExceptionAdvice {
    /**
     * Handle Exception Method
     * @param ex    Exception thrown
     * @return  Response to Restful API call
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception ex) {
        return new ExceptionResponse(ex);
    }
}
