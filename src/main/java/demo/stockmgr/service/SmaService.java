package demo.stockmgr.service;

import demo.stockmgr.config.AppConfig;
import demo.stockmgr.entity.SmaItem;
import demo.stockmgr.util.TaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Service for Simple Moving Average
 * @author Teki Chan
 * @since 30 May 2019
 */
@Service
public class SmaService {
    /**
     * Application Configuration
     */
    @Autowired
    private AppConfig appConfig;

    /** Stock Quote Service */
    @Autowired
    private StockQuoteService stockQuoteService;

    /**
     * Get SMA value list
     * @param stockCode     Stock Code
     * @param fromDateOptional  From Date optional
     * @param toDateOptional    To Date optional
     * @param timeFrameOptional     Optional Time Frame of Simple Moving Average
     * @return  List of SMA value
     * @throws IOException
     * @throws ParseException
     */
    public List<SmaItem> getSmaList(
            String stockCode
            , Optional<String> fromDateOptional
            , Optional<String> toDateOptional
            , Optional<Integer> timeFrameOptional
    ) throws IOException, ParseException {
        List<HistoricalQuote> quoteList = stockQuoteService.getHistoricalQuoteList(stockCode, fromDateOptional, toDateOptional);
        TimeSeries timeSeries = TaUtils.geTimeSeries(quoteList, stockCode);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(timeSeries);

        Integer timeFrame = (timeFrameOptional.isPresent())?timeFrameOptional.get():appConfig.getTa().getDefaultTimeframe();
        SMAIndicator smaIndicator = new SMAIndicator(closePrice, timeFrame);
        List<SmaItem> smaList = new ArrayList<>();
        for(int i=0; i<timeSeries.getBarCount(); i++) {
            SmaItem smaItem = new SmaItem();
            smaItem.setSymbol(stockCode);
            smaItem.setTimeFrame(timeFrame);
            smaItem.setDate(GregorianCalendar.from(timeSeries.getBar(i).getEndTime()));
            smaItem.setSmaValue(smaIndicator.getValue(i).doubleValue());
            smaList.add(smaItem);
        }
        return smaList;
    }
}
