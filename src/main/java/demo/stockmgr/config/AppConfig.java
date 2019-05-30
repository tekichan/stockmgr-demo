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
    private TaConfig ta = new TaConfig();

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
     * Getter of Default From Date in Calendar object for Restful API call
     * @return  Default From Date in Calendar
     * @throws ParseException
     */
    public Calendar getRestDefaultFromCalendar() throws ParseException {
        return DateUtils.getDateFromString(rest.getDefaultFromdate(), rest.getDatePattern(), rest.getTimezone());
    }

    public TaConfig getTa() {
        return ta;
    }

    public void setTa(TaConfig ta) {
        this.ta = ta;
    }
}
