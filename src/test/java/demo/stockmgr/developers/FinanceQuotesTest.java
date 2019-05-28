package demo.stockmgr.developers;

import org.junit.Test;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

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
}
