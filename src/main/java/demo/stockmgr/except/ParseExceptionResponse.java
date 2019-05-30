package demo.stockmgr.except;

import java.util.List;

/**
 * Restful Response for Exception
 * @author Teki Chan
 * @since 29 May 2019
 */
public class ParseExceptionResponse implements IExceptionResponse {
    private String className;
    private String message;
    private List<String> stacktraceList;
    private int errorOffset;

    /**
     * Default Constructor
     */
    private ParseExceptionResponse() {}

    /**
     * Constructor with given Exception detail
     * @param className
     * @param message
     * @param stacktraceList
     */
    public ParseExceptionResponse(String className, String message, List<String> stacktraceList, int errorOffset) {
        this.className = className;
        this.message = message;
        this.stacktraceList = stacktraceList;
        this.errorOffset = errorOffset;
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

    /**
     * Getter of the position where the error was found
     * @return  the position where the error was found
     */
    public int getErrorOffset() {
        return errorOffset;
    }

    /**
     * Setter of the position where the error was found
     * @param errorOffset   the position where the error was found
     */
    public void setErrorOffset(int errorOffset) {
        this.errorOffset = errorOffset;
    }
}
