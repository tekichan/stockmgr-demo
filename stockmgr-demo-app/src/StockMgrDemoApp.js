import React from 'react';
import { Component } from 'react';

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

Highcharts.createElement('link', HIGHCHARTS_ELEMENT, null, document.getElementsByTagName('head')[0]);

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
            , hsOption: stockOptions
            , indicator: 'sma'
            , timeFrame: 20
        };

        this.handleStockDetail = this.handleStockDetail.bind(this);
        this.processStockQuoteTa = this.processStockQuoteTa.bind(this);
        this.errorStockQuote = this.errorStockQuote.bind(this);
        this.handleIndicatorChange = this.handleIndicatorChange.bind(this);
    }

    componentDidMount() {
      this.chartRef = React.createRef();
    }

    handleStockDetail(_event) {
        callStockQuoteTa(
            this.state.stockCode
            , this.state.indicator
            , this.state.timeFrame
            , this.processStockQuoteTa
            , this.errorStockQuote
        );
    }

    handleIndicatorChange(_event) {
        var arr = _event.target.value.split(' ');
        this.setState(
            {
                indicator: arr[0]
                , timeFrame: parseInt(arr[1])
            }
        );
    }

    plotChart(_hsSeries) {
        var self = this;
        var newChange = {
            title: {
                text: self.state.stockCode
            },
            series: _hsSeries
        };
        this.setState({
            hsOption: {...stockOptions, ...newChange}
        });
    }

    processStockQuoteTa(_quoteResp, _taResp) {
        var hsSeries = [];
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

        console.log(hsSeries);
        this.plotChart(hsSeries);
    }

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
        <HighchartsReact
          highcharts={Highcharts}
          constructorType={'stockChart'}
          options={this.state.hsOption}
          ref={this.chartRef}
        />
    </Col>
  </Row>
</Container>
        );
    }
}

// Default exported component
export default StockMgrDemoApp;