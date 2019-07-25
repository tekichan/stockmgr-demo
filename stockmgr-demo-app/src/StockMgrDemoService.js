import axios from 'axios';
import moment from 'moment'

const urlDomain = "http://localhost:8080";
const dateFormat = 'YYYYMMDD';
const maxYearAgo = 5;

export function callStockQuote(stockCode, _cbProcessResp, _cbProcessError) {
    var urlStockQuote = urlDomain + "/rest/stock/"
                        + stockCode
                        + "?fromDate=" + moment().subtract(maxYearAgo, 'year').format(dateFormat)
                        + "&toDate=" + moment().format(dateFormat);
    axios.get(urlStockQuote)
    .then(function (response) {
        _cbProcessResp(response);
    })
    .catch(function (error) {
        _cbProcessError(error);
    })
    .then(function () {
        // always executed
        console.log('GET to [' + urlStockQuote + '] completed');
    });
}

export function callTaIndicator(stockCode, indicator, timeframe, _cbProcessResp, _cbProcessError) {
    var urlTaIndicator = urlDomain + "/rest/" + indicator + "/"
                        + stockCode
                        + "?fromDate=" + moment().subtract(maxYearAgo, 'year').format(dateFormat)
                        + "&toDate=" + moment().format(dateFormat)
                        + "&timeFrame=" + timeframe;
    axios.get(urlTaIndicator)
    .then(function (response) {
        _cbProcessResp(response);
    })
    .catch(function (error) {
        _cbProcessError(error);
    })
    .then(function () {
        // always executed
        console.log('GET to [' + urlTaIndicator + '] completed');
    });
}

export function callStockQuoteTa(stockCode, indicator, timeframe, _cbProcessResp, _cbProcessError) {
    var fromDate = moment().subtract(maxYearAgo, 'year').format(dateFormat)
    var toDate = moment().format(dateFormat);
    var urlStockQuote = urlDomain + "/rest/stock/"
                        + stockCode
                        + "?fromDate=" + fromDate
                        + "&toDate=" + toDate;
    var urlTaIndicator = urlDomain + "/rest/" + indicator + "/"
                        + stockCode
                        + "?fromDate=" + fromDate
                        + "&toDate=" + toDate
                        + "&timeFrame=" + timeframe;

    axios.all([
        axios.get(urlStockQuote)
        , axios.get(urlTaIndicator)
    ])
    .then(axios.spread(function (_quoteResp, _taResp) {
        _cbProcessResp(_quoteResp, _taResp)
    }))
    .catch(function (error) {
        _cbProcessError(error);
    })
    .then(function () {
        // always executed
        console.log('GET to [' + urlStockQuote + '] and [' + urlTaIndicator + '] completed');
    });
}