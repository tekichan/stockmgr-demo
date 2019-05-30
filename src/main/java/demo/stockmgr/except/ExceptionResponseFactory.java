package demo.stockmgr.except;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Factory Class of Exception Response
 * <p>
 *     This Factory Class is not a must in this project
 *     but for demonstrating Encapsulation in Factory Design Pattern.
 * </p>
 * @author Teki Chan
 * @since 30 May 2019
 */
public class ExceptionResponseFactory {
    /**
     * Parse Exception object to Exception Response
     * @param ex    Exception object thrown
     */
    public static IExceptionResponse toExceptionResponse(Exception ex) {
        if (ex instanceof ParseException) {
            ParseException pex = (ParseException) ex;
            return new ParseExceptionResponse(
                    pex.getClass().getName()
                    , pex.getMessage()
                    , Arrays.stream(pex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
                    , pex.getErrorOffset()
            );
        } else {
            return new ExceptionResponse(
                    ex.getClass().getName()
                    , ex.getMessage()
                    , Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
            );
        }
    }
}
