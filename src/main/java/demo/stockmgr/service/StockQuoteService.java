package demo.stockmgr.service;

import demo.stockmgr.config.AppConfig;
import demo.stockmgr.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Stock Quote related Service
 * @author Teki Chan
 * @since 30 May 2019
 */
@Service
public class StockQuoteService {
    /**
     * Application Configuration
     */
    @Autowired
    private AppConfig appConfig;

    /**
     * Get Historical Quote List
     * @param stockCode         Stock Code
     * @param fromDateOptional  From Date Optional
     * @param toDateOptional    To Date Optional
     * @return  List of Historical Quote
     * @throws IOException
     * @throws ParseException
     */
    public List<HistoricalQuote> getHistoricalQuoteList(
            String stockCode
            , Optional<String> fromDateOptional
            , Optional<String> toDateOptional
    ) throws IOException, ParseException {
        Calendar fromDateCal = DateUtils.getDateFromOptional(fromDateOptional, appConfig.getRest().getDatePattern(), appConfig.getRest().getTimezone()); // Get From Date
        Calendar toDateCal = DateUtils.getDateFromOptional(toDateOptional, appConfig.getRest().getDatePattern(), appConfig.getRest().getTimezone()); // Get To Date
        return getHistoricalQuoteList(stockCode, fromDateCal, toDateCal);
    }

    /**
     * Get Historical Quote List
     * @param stockCode         Stock Code
     * @param fromDate  From Date String
     * @param toDate   To Date String
     * @return  List of Historical Quote
     * @throws IOException
     * @throws ParseException
     */
    public List<HistoricalQuote> getHistoricalQuoteList(
            String stockCode
            , String fromDate
            , String toDate
    ) throws IOException, ParseException {
        Calendar fromDateCal = DateUtils.getDateFromString(fromDate, appConfig.getRest().getDatePattern(), appConfig.getRest().getTimezone()); // Get From Date
        Calendar toDateCal = DateUtils.getDateFromString(toDate, appConfig.getRest().getDatePattern(), appConfig.getRest().getTimezone()); // Get To Date
        return getHistoricalQuoteList(stockCode, fromDateCal, toDateCal);
    }

    /**
     * Get Historical Quote List
     * @param stockCode         Stock Code
     * @param fromDateCal  From Date Calendar
     * @param toDateCal   To Date Calendar
     * @return  List of Historical Quote
     * @throws IOException
     * @throws ParseException
     */
    public List<HistoricalQuote> getHistoricalQuoteList(
            String stockCode
            , Calendar fromDateCal
            , Calendar toDateCal
    ) throws IOException, ParseException {
        Stock stockObject = YahooFinance.get(stockCode);    // Get Stock Code
        List<HistoricalQuote> quoteList;
        if (fromDateCal == null && toDateCal == null) {
            // No Date parameter is given, return the default history
            quoteList = stockObject.getHistory(Interval.DAILY);
        } else if (fromDateCal == null) {
            // Only To Date parameter is given, return the history from the very beginning
            quoteList = stockObject.getHistory(appConfig.getRestDefaultFromCalendar(), toDateCal, Interval.DAILY);
        } else if (toDateCal == null) {
            // Only From Date parameter is given, return the history till today
            quoteList = stockObject.getHistory(fromDateCal, Interval.DAILY);
        } else {
            // Both Date parameters are given, return the history ranged
            quoteList = stockObject.getHistory(fromDateCal, toDateCal, Interval.DAILY);
        }
        return quoteList.stream().filter(quote -> quote.getClose() != null).collect(Collectors.toList());
    }
}
