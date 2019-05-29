package demo.stockmgr.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Restful Response for Exception
 * @author Teki Chan
 * @since 29 May 2019
 */
public class ExceptionResponse {
    private String className;
    private String message;
    private List<String> stacktraceList;

    /**
     * Default Constructor
     */
    public ExceptionResponse() {}

    /**
     * Constructor with given Exception
     * @param ex    Given Exception
     */
    public ExceptionResponse(Exception ex) {
        this.parseException(ex);
    }

    /**
     * Parse Exception object to Exception Response
     * @param ex    Exception object thrown
     */
    public void parseException(Exception ex) {
        this.className = ex.getClass().getName();
        this.message = ex.getMessage();
        this.stacktraceList = Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
    }

    /**
     * Getter of Response Message
     * @return  Response Message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter of Response Message
     * @param message   Response Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter of List of Exception Stack Trace
     * @return  List of Exception Stack Trace
     */
    public List<String> getStacktraceList() {
        return stacktraceList;
    }

    /**
     * Setter of List of Exception Stack Trace
     * @param stacktraceList    List of Exception Stack Trace
     */
    public void setStacktraceList(List<String> stacktraceList) {
        this.stacktraceList = stacktraceList;
    }

    /**
     * Getter of Exception Class Name
     * @return  Exception Class Name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Setter of Exception Class Name
     * @param className Exception Class Name
     */
    public void setClassName(String className) {
        this.className = className;
    }
}
