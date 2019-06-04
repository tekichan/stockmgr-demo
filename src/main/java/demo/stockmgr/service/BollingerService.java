package demo.stockmgr.service;

import demo.stockmgr.config.AppConfig;
import demo.stockmgr.entity.BollingerItem;
import demo.stockmgr.entity.SmaItem;
import demo.stockmgr.util.TaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for Bollinger indicators
 * @author Teki Chan
 * @since 4 Jun 2019
 */
@Service
public class BollingerService {
    /**
     * Application Configuration
     */
    @Autowired
    private AppConfig appConfig;

    /** Stock Quote Service */
    @Autowired
    private StockQuoteService stockQuoteService;

    /**
     * Get Bollinger values list
     * @param stockCode     Stock Code
     * @param fromDateOptional  From Date optional
     * @param toDateOptional    To Date optional
     * @param timeFrameOptional     Optional Time Frame of Bollinger
     * @return  List of Bollinger values
     * @throws IOException
     * @throws ParseException
     */
    public List<BollingerItem> getBollingerList(
            String stockCode
            , Optional<String> fromDateOptional
            , Optional<String> toDateOptional
            , Optional<Integer> timeFrameOptional
    ) throws IOException, ParseException {
        List<HistoricalQuote> quoteList = stockQuoteService.getHistoricalQuoteList(stockCode, fromDateOptional, toDateOptional);
        TimeSeries timeSeries = TaUtils.geTimeSeries(quoteList, stockCode);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(timeSeries);

        Integer timeFrame = (timeFrameOptional.isPresent())?timeFrameOptional.get():appConfig.getTa().getDefaultTimeframe();

        EMAIndicator emaValue = new EMAIndicator(closePrice, timeFrame);
        StandardDeviationIndicator sdValue = new StandardDeviationIndicator(closePrice, timeFrame);

        // Bollinger bands
        BollingerBandsMiddleIndicator middleBBand = new BollingerBandsMiddleIndicator(emaValue);
        BollingerBandsLowerIndicator lowBBand = new BollingerBandsLowerIndicator(middleBBand, sdValue);
        BollingerBandsUpperIndicator upBBand = new BollingerBandsUpperIndicator(middleBBand, sdValue);

        List<BollingerItem> bollingerList = new ArrayList<>();
        for(int i=0; i<timeSeries.getBarCount(); i++) {
            BollingerItem bollingerItem = new BollingerItem();
            bollingerItem.setSymbol(stockCode);
            bollingerItem.setTimeFrame(timeFrame);
            bollingerItem.setDateTime(timeSeries.getBar(i).getEndTime());
            bollingerItem.setMiddleBBand(middleBBand.getValue(i).doubleValue());
            bollingerItem.setLowerBBand(lowBBand.getValue(i).doubleValue());
            bollingerItem.setUpperBBand(upBBand.getValue(i).doubleValue());
            bollingerList.add(bollingerItem);
        }
        return bollingerList;
    }
}
