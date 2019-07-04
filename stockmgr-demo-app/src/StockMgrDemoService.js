import axios from 'axios';
import moment from 'moment'

const urlDomain = "https://localhost:8080";
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