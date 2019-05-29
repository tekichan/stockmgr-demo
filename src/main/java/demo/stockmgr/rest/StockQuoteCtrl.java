package demo.stockmgr.rest;

import demo.stockmgr.config.AppConfig;
import demo.stockmgr.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Stock Quote Controller
 * @since Teki Chan
 * @since 28 May 2019
 */
@RestController
public class StockQuoteCtrl {
    /** Path Variable for Stock Code */
    private static final String PATHVAR_STOCKCODE = "stockCode";
    /** URL of Stock Quote */
    private static final String URL_STOCKQUOTE = AppConfig.REST_PREFIX + "/stock/{" + PATHVAR_STOCKCODE + "}";
    /** Parameter for From Date */
    private static final String PARAM_FROMDATE = "fromDate";
    /** Parameter for To Date */
    private static final String PARAM_TODATE = "toDate";

    /**
     * Application Configuration
     */
    @Autowired
    private AppConfig appConfig;

    /**
     * Endpoint of getting List of Historical Stock Quote
     * @param stockCode     Stock Code
     * @param fromDateOptional  From Date String (optional)
     * @param toDateOptional    To Date String (optional)
     * @return  List of Historical Stock Quote
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(
            value={
                    URL_STOCKQUOTE
            }
            , method=RequestMethod.GET
            , produces={
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    @ResponseBody
    public List<HistoricalQuote> getStockQuote(
            @PathVariable(PATHVAR_STOCKCODE) String stockCode
            , @RequestParam(PARAM_FROMDATE) Optional<String> fromDateOptional
            , @RequestParam(PARAM_TODATE) Optional<String> toDateOptional
    ) throws IOException, ParseException {
        Stock stockObject = YahooFinance.get(stockCode);    // Get Stock Code
        Calendar fromDateCal = DateUtils.getDateFromOptional(fromDateOptional, appConfig.getRestDatePattern()); // Get From Date
        Calendar toDateCal = DateUtils.getDateFromOptional(toDateOptional, appConfig.getRestDatePattern()); // Get To Date

        if (fromDateCal == null && toDateCal == null) {
            // No Date parameter is given, return the default history
            return stockObject.getHistory(Interval.DAILY);
        } else if (fromDateCal == null) {
            // Only To Date parameter is given, return the history from the very beginning
            return stockObject.getHistory(appConfig.getRestDefaultFromCalendar(), toDateCal, Interval.DAILY);
        } else if (toDateCal == null) {
            // Only From Date parameter is given, return the history till today
            return stockObject.getHistory(fromDateCal, Interval.DAILY);
        } else {
            // Both Date parameters are given, return the history ranged
            return stockObject.getHistory(fromDateCal, toDateCal, Interval.DAILY);
        }
    }
}
