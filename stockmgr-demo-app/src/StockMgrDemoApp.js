import React from 'react';
import { Component } from 'react';
import ReactDOM from 'react-dom'

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import HighchartsReact from 'highcharts-react-official'
import Highcharts from 'highcharts/highstock'

import moment from 'moment'

import { callStockQuoteTa } from './StockMgrDemoService.js';
import { HIGHCHARTS_ELEMENT, HIGHCHARTS_DARK_THEME } from './HighStockConstants.js';

// Load Highcharts modules
require('highcharts/indicators/indicators')(Highcharts)
require('highcharts/indicators/pivot-points')(Highcharts)
require('highcharts/indicators/macd')(Highcharts)
require('highcharts/modules/exporting')(Highcharts)
require('highcharts/modules/map')(Highcharts)

// Basis of Options of Highstock
const stockOptions = {
  rangeSelector: {
      selected: 2
  },
  plotOptions: {
    series: {
        dataGrouping: {
            units: [
                [
                    'week',
                     [1]
                ], [
                    'month',
                    [1, 2, 3, 4, 6]
                ]
            ]
        }
    }
  },
  yAxis: [{
    startOnTick: false,
    endOnTick: false,
    labels: {
        align: 'right',
        x: -3
    },
    title: {
        text: 'Price'
    },
    height: '60%',
    lineWidth: 2,
    resize: {
        enabled: true
    }
  }, {
    labels: {
        align: 'right',
        x: -3
    },
    title: {
        text: 'Volume'
    },
    top: '65%',
    height: '35%',
    offset: 0,
    lineWidth: 2
  }]
}

// Create Element for Highcharts
Highcharts.createElement('link', HIGHCHARTS_ELEMENT, null, document.getElementsByTagName('head')[0]);

// Assign Dark theme to Highcharts
Highcharts.theme = HIGHCHARTS_DARK_THEME;

// Apply the theme
Highcharts.setOptions(Highcharts.theme);

/**
 * Main Application Page for Stock Manager Demo App
 * @author Teki Chan
 * @since 2 Jul 2019
 */
class StockMgrDemoApp extends Component {
    /**
     * Default Constructor
     * @param {*} props     Inbound Properties
     */
    constructor(props) {
        super(props);

        this.state = {
            stockCode: ''
            , indicator: 'sma'
            , timeFrame: 20
        };

        this.handleStockDetail = this.handleStockDetail.bind(this);
        this.processStockQuoteTa = this.processStockQuoteTa.bind(this);
        this.errorStockQuote = this.errorStockQuote.bind(this);
        this.handleIndicatorChange = this.handleIndicatorChange.bind(this);
    }

    /**
     * Mount element when the page is ready
     */
    componentDidMount() {
        this.chartRef = React.createRef();
        this.hsElement = React.createElement(
            HighchartsReact
            , {
                highcharts: Highcharts
                , constructorType: 'stockChart'
                , options: stockOptions
                , ref: this.chartRef
            }
        );
        ReactDOM.render(this.hsElement, document.getElementById('divHighStock'));
    }

    /**
     * Handler of Stock Detail to be called
     * @param _event    Event
     */
    handleStockDetail(_event) {
        callStockQuoteTa(
            this.state.stockCode
            , this.state.indicator
            , this.state.timeFrame
            , this.processStockQuoteTa
            , this.errorStockQuote
        );
    }

    /**
     * Handler of Changing Indicator Selector
     * @param _event    Event
     */
    handleIndicatorChange(_event) {
        var arr = _event.target.value.split(' ');
        this.setState(
            {
                indicator: arr[0]
                , timeFrame: parseInt(arr[1])
            }
        );
    }

    /**
     * Plot a chart based on given Highstock data series
     * @param _hsSeries     Highstock data series
     */
    plotChart(_hsSeries) {
        var self = this;
        var newChange = {
            title: {
                text: self.state.stockCode
            },
            series: _hsSeries
        };
        var hsOption = {...stockOptions, ...newChange};

        ReactDOM.unmountComponentAtNode(document.getElementById('divHighStock'));
        this.hsElement = React.createElement(
            HighchartsReact
            , {
                highcharts: Highcharts
                , constructorType: 'stockChart'
                , options: hsOption
                , ref: this.chartRef
            }
        );
        ReactDOM.render(this.hsElement, document.getElementById('divHighStock'));
    }

    /**
     * Processor of Stock Quote and TA
     * @param _quoteResp    Stock Quote Response
     * @param _taResp       TA Response
     */
    processStockQuoteTa(_quoteResp, _taResp) {
        var hsSeries = [];

        // Process Stock Quotes
        var stockPrices = _quoteResp['data'].map(
            (_item, _idx, _arr) => {
                return [
                    moment(_item['date']).toDate().getTime()
                   , _item['open']
                   , _item['high']
                   , _item['low']
                   , _item['close']
                ];
            }
        );
        hsSeries.push(
            {
                type: 'candlestick',
                name: 'Stock Price',
                data: stockPrices,
                id: 'stock_price',
                zIndex: 2,
                yAxis: 0
            }
        );

        // Process Stock Volume
        var volumes = _quoteResp['data'].map(
            (_item, _idx, _arr) => {
                return [
                    moment(_item['date']).toDate().getTime()
                    , _item['volume']
                ];
            }
        );
        hsSeries.push(
            {
                type: 'column',
                name: 'Volume',
                data: volumes,
                id: 'volume',
                linkedTo: 'stock_price',
                yAxis: 1
            }
        );

        if (this.state.indicator === 'bband') {
            // Process Upper BBand
            var upperBBand = _taResp['data'].map(
                (_item, _idx, _arr) => {
                    return [
                        moment(_item['date']).toDate().getTime()
                        , _item['upperBBand']
                    ];
                }
            );
            hsSeries.push(
                {
                    type: 'line',
                    name: 'Upper BBand',
                    id: 'upperBBand',
                    data: upperBBand,
                    linkedTo: 'stock_price',
                    zIndex: 1,
                    yAxis: 0
                }
            );

            // Process Middle BBand
            var middleBBand = _taResp['data'].map(
                (_item, _idx, _arr) => {
                    return [
                        moment(_item['date']).toDate().getTime()
                        , _item['middleBBand']
                    ];
                }
            );
            hsSeries.push(
                {
                    type: 'line',
                    name: 'Middle BBand',
                    id: 'middleBBand',
                    data: middleBBand,
                    linkedTo: 'stock_price',
                    zIndex: 1,
                    yAxis: 0
                }
            );

            // Process Lower BBand
            var lowerBBand = _taResp['data'].map(
                (_item, _idx, _arr) => {
                    return [
                        moment(_item['date']).toDate().getTime()
                        , _item['lowerBBand']
                    ];
                }
            );
            hsSeries.push(
                {
                    type: 'line',
                    name: 'Lower BBand',
                    id: 'lowerBBand',
                    data: lowerBBand,
                    linkedTo: 'stock_price',
                    zIndex: 1,
                    yAxis: 0
                }
            );

            console.log('Adding Bollinger Band');
        } else if (this.state.indicator === 'sma') {
            // Process SMA
            var smaValue = _taResp['data'].map(
                (_item, _idx, _arr) => {
                    return [
                        moment(_item['date']).toDate().getTime()
                        , _item['smaValue']
                    ];
                }
            );
            hsSeries.push(
                {
                    type: 'line',
                    name: 'SMA',
                    id: 'sma',
                    data: smaValue,
                    linkedTo: 'stock_price',
                    zIndex: 1
                    , yAxis: 0
                }
            );

            console.log('Adding Simple Moving Average');
        }

        this.plotChart(hsSeries);
    }

    /**
     * Processor of Error Stock Quote
     * @param _error    Error Response
     */
    errorStockQuote(_error) {
        console.log(_error);
    }

    /**
     * React JS Render function
     */
    render() {
        return (
<Container>
  <Form>
  <Row>
    <Col>
      <Form.Control placeholder="Stock Code"
                    value={this.state.stockCode}
                    onChange={
                        (_event) => {
                            this.setState({
                                stockCode: _event.target.value
                            });
                        }
                    }
      />
    </Col>
    <Col>
      <Form.Control
            as="select"
            value={this.state.indicator + ' ' + this.state.timeFrame}
            onChange={this.handleIndicatorChange}>
        <option value='sma 20'>SMA 20</option>
        <option value='sma 100'>SMA 100</option>
        <option value='sma 250'>SMA 250</option>
        <option value='bband 20'>BBand 20</option>
      </Form.Control>
    </Col>
    <Col>
       <Button variant="primary" type="button" onClick={this.handleStockDetail}>Refresh</Button>
    </Col>
  </Row>
  </Form>
  <Row>
    <Col>
        <div id='divHighStock'></div>
    </Col>
  </Row>
</Container>
        );
    }
}

// Default exported component
export default StockMgrDemoApp;