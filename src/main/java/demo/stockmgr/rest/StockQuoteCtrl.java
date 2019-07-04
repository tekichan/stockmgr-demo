package demo.stockmgr.rest;

import demo.stockmgr.config.AppConfig;
import demo.stockmgr.service.StockQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.text.ParseException;
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

    /** Stock Quote Service */
    @Autowired
    private StockQuoteService stockQuoteService;

    /**
     * Endpoint of getting List of Historical Stock Quote
     * @param stockCode     Stock Code
     * @param fromDateOptional  From Date String (optional)
     * @param toDateOptional    To Date String (optional)
     * @return  List of Historical Stock Quote
     * @throws IOException
     * @throws ParseException
     */
    @GetMapping(
            value={
                    URL_STOCKQUOTE
            }
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
        return stockQuoteService.getHistoricalQuoteList(stockCode, fromDateOptional, toDateOptional);
    }
}
