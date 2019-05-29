package demo.stockmgr.developers;

import org.junit.Test;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

import java.util.Calendar;
import java.util.List;

/**
 * Playground Unit Test for Finance Quotes API
 * @author Teki Chan
 * @since 28 May 2019
 */
public class FinanceQuotesTest {
    @Test
    public void testSingleStockPrint() throws Exception {
        Stock stock = YahooFinance.get("0005.HK", true);
        stock.print();
    }

    @Test
    public void testFxQuotePrint() throws Exception {
        FxQuote usdToHkd = YahooFinance.getFx(FxSymbols.USDHKD);
        System.out.println(usdToHkd);
    }

    @Test
    public void testSingleStockHistoryQuote() throws Exception {
        // Get From Date
        Calendar fromCal = Calendar.getInstance();
        fromCal.add(Calendar.DATE, -7);  // a week before
        fromCal.set(Calendar.HOUR, 0);
        fromCal.set(Calendar.MINUTE, 0);
        fromCal.set(Calendar.SECOND, 0);
        fromCal.set(Calendar.MILLISECOND, 0);
        System.out.println("From Calendar Timezone: " + fromCal.getTimeZone().getDisplayName());

        // Get Stock Object
        Stock stock = YahooFinance.get("0005.HK");
        List<HistoricalQuote> listQuotes = stock.getHistory(fromCal, Interval.DAILY);
        System.out.println("Historical Quotes: ");
        listQuotes.forEach(System.out::println);
    }
}
