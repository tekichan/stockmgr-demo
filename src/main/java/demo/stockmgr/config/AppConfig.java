package demo.stockmgr.config;

import demo.stockmgr.util.DateUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Application Level Configuration
 * @since Teki Chan
 * @since 29 May 2019
 */
@Component
@ConfigurationProperties(prefix="stockmgr")
public class AppConfig {
    /** Restful URL Prefix */
    public static final String REST_PREFIX = "/rest";

    private RestConfig rest = new RestConfig();

    /**
     * Setter of Restful related configuration
     * @param rest  Restful related configuration
     */
    public void setRest(RestConfig rest) { this.rest = rest; }

    /**
     * Getter of Restful related configuration
     * @return  Restful related configuration
     */
    public RestConfig getRest() { return this.rest; }

    /**
     * Getter of Date Pattern used in Restful API call
     * @return  Date Pattern
     */
    public String getRestDatePattern() { return rest.getDatePattern(); }

    /**
     * Getter of Default From Date in Calendar object for Restful API call
     * @return  Default From Date in Calendar
     * @throws ParseException
     */
    public Calendar getRestDefaultFromCalendar() throws ParseException {
        return DateUtils.getDateFromString(rest.getDefaultFromdate(), getRestDatePattern());
    }

    /**
     * Restful related configuration
     */
    public static class RestConfig {
        private String datePattern;
        private String defaultFromdate;

        /**
         * Getter of Date Pattern
         * @return  Date Pattern
         */
        public String getDatePattern() {
            return datePattern;
        }

        /**
         * Setter of Date Pattern
         * @param datePattern   Date Pattern
         */
        public void setDatePattern(String datePattern) {
            this.datePattern = datePattern;
        }

        /**
         * Getter of Default From Date String
         * @return  Default From Date String
         */
        public String getDefaultFromdate() {
            return defaultFromdate;
        }

        /**
         * Setter of Default From Date String
         * @param defaultFromdate   Default From Date String
         */
        public void setDefaultFromdate(String defaultFromdate) {
            this.defaultFromdate = defaultFromdate;
        }
    }
}
