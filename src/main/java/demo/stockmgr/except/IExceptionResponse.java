package demo.stockmgr.except;

import java.util.List;

/**
 * Interface for Exception Response
 * @author Teki Chan
 * @since 30 May 2019
 */
public interface IExceptionResponse {
    /**
     * Getter of Response Message
     * @return  Response Message
     */
    String getMessage();
    /**
     * Getter of List of Exception Stack Trace
     * @return  List of Exception Stack Trace
     */
    List<String> getStacktraceList();
    /**
     * Getter of Exception Class Name
     * @return  Exception Class Name
     */
    String getClassName();
}
