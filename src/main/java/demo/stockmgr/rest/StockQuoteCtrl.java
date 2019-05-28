package demo.stockmgr.rest;

import org.springframework.web.bind.annotation.RestController;

/**
 * Stock Quote Controller
 * @since Teki Chan
 * @since 28 May 2019
 */
@RestController
public class StockQuoteCtrl {
    public static final String PATHVAR_STOCKCODE = "stockCode";
    public static final String URL_STOCKQUOTE = "/rest/stock/{" + PATHVAR_STOCKCODE + "}";
    public static final String PARAM_FROMDATE = "fromDate";
    public static final String PARAM_TODATE = "toDate";
}
