package demo.stockmgr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(TaUtils.class);

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
        quoteList.stream().filter(quote -> quote.getClose() != null).forEach(quote -> {
                try {
                    series.addBar(
                            DateUtils.getZonedDateTime(
                                    quote.getDate()
                            )
                            , quote.getOpen()
                            , quote.getHigh()
                            , quote.getLow()
                            , quote.getClose()
                            , quote.getVolume()
                    );
                } catch (Exception objEx) {
                    // Skip if exception happens
                    logger.warn("Error in converting a quote record: " + quote.toString(), objEx);
                }
            }
        );
        return series;
    }
}
