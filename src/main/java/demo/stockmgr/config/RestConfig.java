package demo.stockmgr.config;

/**
 * Restful related configuration
 * @since Teki Chan
 * @since 29 May 2019
 */
public class RestConfig {
    private String datePattern;
    private String defaultFromdate;
    private String timezone;
    private String allowedCors;

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

    /**
     * Getter of Timezone ID
     * @return  Timezone ID
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Setter of Timezone ID
     * @param timezone  Timezone ID
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * Getter of Allowed CORS
     * @return  Allowed CORS
     */
    public String getAllowedCors() {
        return allowedCors;
    }

    /**
     * Setter of Allowed CORS
     * @param allowedCors   Allowed CORS
     */
    public void setAllowedCors(String allowedCors) {
        this.allowedCors = allowedCors;
    }
}