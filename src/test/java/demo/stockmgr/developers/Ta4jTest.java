package demo.stockmgr.developers;

import org.junit.Test;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Playground Unit Test for TA4j
 * @author Teki Chan
 * @since 30 May 2019
 */
public class Ta4jTest {
    @Test
    public void testSmaIndicator() throws Exception {
        TimeSeries series = new BaseTimeSeries.SeriesBuilder().withName("0001.HK").build();
        series.addBar(getZonedDateTime("2019-05-28 16:00:00"), 73.800003, 74.900002, 73.599998, 74.900002, 13063303);
        series.addBar(getZonedDateTime("2019-05-29 16:00:00"), 74.5, 75, 73.849998, 74.300003, 5317107);
        series.addBar(getZonedDateTime("2019-05-30 16:00:00"), 74, 74.849998, 73, 73, 5880761);
        series.addBar(getZonedDateTime("2019-06-03 16:00:00"), 73.800003, 74.900002, 73.599998, 74.900002, 13063303);
        series.addBar(getZonedDateTime("2019-06-04 16:00:00"), 74.5, 75, 73.849998, 74.300003, 5317107);
        series.addBar(getZonedDateTime("2019-06-05 16:00:00"), 74, 74.849998, 73, 73, 5880761);

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
        for(int i=0; i<series.getBarCount(); i++) {
            System.out.println(shortSma.getValue(i));
        }
    }

    @Test
    public void testBb14Indicator() throws Exception {
        TimeSeries series = new BaseTimeSeries.SeriesBuilder().withName("0001.HK").build();
        series.addBar(getZonedDateTime("2019-05-28 16:00:00"), 73.800003, 74.900002, 73.599998, 74.900002, 13063303);
        series.addBar(getZonedDateTime("2019-05-29 16:00:00"), 74.5, 75, 73.849998, 74.300003, 5317107);
        series.addBar(getZonedDateTime("2019-05-30 16:00:00"), 74, 74.849998, 73, 73, 5880761);
        series.addBar(getZonedDateTime("2019-06-03 16:00:00"), 73.800003, 74.900002, 73.599998, 74.900002, 13063303);
        series.addBar(getZonedDateTime("2019-06-04 16:00:00"), 74.5, 75, 73.849998, 74.300003, 5317107);
        series.addBar(getZonedDateTime("2019-06-05 16:00:00"), 74, 74.849998, 73, 73, 5880761);

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator avg14 = new EMAIndicator(closePrice, 14);
        StandardDeviationIndicator sd14 = new StandardDeviationIndicator(closePrice, 14);

        // Bollinger bands
        BollingerBandsMiddleIndicator middleBBand = new BollingerBandsMiddleIndicator(avg14);
        BollingerBandsLowerIndicator lowBBand = new BollingerBandsLowerIndicator(middleBBand, sd14);
        BollingerBandsUpperIndicator upBBand = new BollingerBandsUpperIndicator(middleBBand, sd14);

        for(int i=0; i<series.getBarCount(); i++) {
            System.out.println(lowBBand.getValue(i) + "-->" + upBBand.getValue(i));
        }
    }

    private ZonedDateTime getZonedDateTime(String dt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime zdt = ZonedDateTime.parse(dt, formatter.withZone(ZoneId.of("Hongkong")));
        return zdt;
    }
}
