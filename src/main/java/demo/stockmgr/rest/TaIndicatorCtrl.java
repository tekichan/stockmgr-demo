package demo.stockmgr.rest;

import demo.stockmgr.config.AppConfig;
import demo.stockmgr.entity.SmaItem;
import demo.stockmgr.service.SmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * Technical Analysis Indicator Controller
 * @since Teki Chan
 * @since 30 May 2019
 */
@RestController
public class TaIndicatorCtrl {
    /** Path Variable for Stock Code */
    private static final String PATHVAR_STOCKCODE = "stockCode";
    /** URL of Simple moving average (SMA) indicator. */
    private static final String URL_SMA = AppConfig.REST_PREFIX + "/sma/{" + PATHVAR_STOCKCODE + "}";
    /** Parameter for From Date */
    private static final String PARAM_FROMDATE = "fromDate";
    /** Parameter for To Date */
    private static final String PARAM_TODATE = "toDate";
    /** Parameter for Time Frame */
    private static final String PARAM_TIMEFRAME = "timeFrame";

    /** Stock Quote Service */
    @Autowired
    private SmaService smaService;

    /**
     * Endpoint of getting List of SMA value
     * @param stockCode     Stock Code
     * @param fromDateOptional  From Date String (optional)
     * @param toDateOptional    To Date String (optional)
     * @return  List of Historical Stock Quote
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(
            value={
                    URL_SMA
            }
            , method=RequestMethod.GET
            , produces={
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    @ResponseBody
    public List<SmaItem> getStockQuote(
            @PathVariable(PATHVAR_STOCKCODE) String stockCode
            , @RequestParam(PARAM_FROMDATE) Optional<String> fromDateOptional
            , @RequestParam(PARAM_TODATE) Optional<String> toDateOptional
            , @RequestParam(PARAM_TIMEFRAME) Optional<Integer> timeFrameOptional
    ) throws IOException, ParseException {
        return smaService.getSmaList(stockCode, fromDateOptional, toDateOptional, timeFrameOptional);
    }
}
