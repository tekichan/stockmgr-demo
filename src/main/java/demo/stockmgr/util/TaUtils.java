package demo.stockmgr.util;

import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.List;

/**
 * Technical Analysis Utility
 * @author Teki Chan
 * @since 30 May 2019
 */
public final class TaUtils {
    /**
     * Private constructor for utility class
     */
    private TaUtils() {}

    /**
     * Create TimeSeries object by given Historical Quote List and Stock Symbol
     * @param quoteList List of Historical Quote
     * @param symbol    Stock Symbol
     * @return  TimeSeries object
     */
    public static TimeSeries geTimeSeries(List<HistoricalQuote> quoteList, String symbol) {
        TimeSeries series = new BaseTimeSeries.SeriesBuilder().withName(symbol).build();
        quoteList.forEach(quote ->
                series.addBar(
                        DateUtils.getZonedDateTime(quote.getDate())
                        , quote.getOpen()
                        , quote.getHigh()
                        , quote.getLow()
                        , quote.getClose()
                        , quote.getVolume()
                )
        );
        return series;
    }
}
