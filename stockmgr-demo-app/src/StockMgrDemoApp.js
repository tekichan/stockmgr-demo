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

// Load Highcharts modules
require('highcharts/indicators/indicators')(Highcharts)
require('highcharts/indicators/pivot-points')(Highcharts)
require('highcharts/indicators/macd')(Highcharts)
require('highcharts/modules/exporting')(Highcharts)
require('highcharts/modules/map')(Highcharts)

const StockChart = ({ options, highcharts }) => <HighchartsReact
  highcharts={highcharts}
  constructorType={'stockChart'}
  options={options}
  oneToOne={false}
/>

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
  }],
  series: [{
    data: [
        [0,1,1,1,1]
    ],
    type: 'candlestick',
    name: 'N/A Stock Price',
    id: 'stock_price',
    zIndex: 2
  }, {
    data: [
        [0,0]
    ],
    type: 'column',
    name: 'Volume',
    id: 'volume',
    linkedTo: 'stock_price',
    yAxis: 1
  }]
}

Highcharts.createElement('link', {
    href: 'https://fonts.googleapis.com/css?family=Unica+One',
    rel: 'stylesheet',
    type: 'text/css'
}, null, document.getElementsByTagName('head')[0]);

Highcharts.theme = {
    colors: ['#2b908f', '#90ee7e', '#f45b5b', '#7798BF', '#aaeeee', '#ff0066',
        '#eeaaee', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'],
    chart: {
        backgroundColor: {
            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
            stops: [
                [0, '#2a2a2b'],
                [1, '#3e3e40']
            ]
        },
        style: {
            fontFamily: '\'Unica One\', sans-serif'
        },
        plotBorderColor: '#606063'
    },
    title: {
        style: {
            color: '#E0E0E3',
            textTransform: 'uppercase',
            fontSize: '20px'
        }
    },
    subtitle: {
        style: {
            color: '#E0E0E3',
            textTransform: 'uppercase'
        }
    },
    xAxis: {
        gridLineColor: '#707073',
        labels: {
            style: {
                color: '#E0E0E3'
            }
        },
        lineColor: '#707073',
        minorGridLineColor: '#505053',
        tickColor: '#707073',
        title: {
            style: {
                color: '#A0A0A3'

            }
        }
    },
    yAxis: {
        gridLineColor: '#707073',
        labels: {
            style: {
                color: '#E0E0E3'
            }
        },
        lineColor: '#707073',
        minorGridLineColor: '#505053',
        tickColor: '#707073',
        tickWidth: 1,
        title: {
            style: {
                color: '#A0A0A3'
            }
        }
    },
    tooltip: {
        backgroundColor: 'rgba(0, 0, 0, 0.85)',
        style: {
            color: '#F0F0F0'
        }
    },
    plotOptions: {
        series: {
            dataLabels: {
                color: '#B0B0B3'
            },
            marker: {
                lineColor: '#333'
            }
        },
        boxplot: {
            fillColor: '#505053'
        },
        candlestick: {
            lineColor: 'white'
        },
        errorbar: {
            color: 'white'
        }
    },
    legend: {
        itemStyle: {
            color: '#E0E0E3'
        },
        itemHoverStyle: {
            color: '#FFF'
        },
        itemHiddenStyle: {
            color: '#606063'
        }
    },
    credits: {
        style: {
            color: '#666'
        }
    },
    labels: {
        style: {
            color: '#707073'
        }
    },

    drilldown: {
        activeAxisLabelStyle: {
            color: '#F0F0F3'
        },
        activeDataLabelStyle: {
            color: '#F0F0F3'
        }
    },

    navigation: {
        buttonOptions: {
            symbolStroke: '#DDDDDD',
            theme: {
                fill: '#505053'
            }
        }
    },

    // scroll charts
    rangeSelector: {
        buttonTheme: {
            fill: '#505053',
            stroke: '#000000',
            style: {
                color: '#CCC'
            },
            states: {
                hover: {
                    fill: '#707073',
                    stroke: '#000000',
                    style: {
                        color: 'white'
                    }
                },
                select: {
                    fill: '#000003',
                    stroke: '#000000',
                    style: {
                        color: 'white'
                    }
                }
            }
        },
        inputBoxBorderColor: '#505053',
        inputStyle: {
            backgroundColor: '#333',
            color: 'silver'
        },
        labelStyle: {
            color: 'silver'
        }
    },

    navigator: {
        handles: {
            backgroundColor: '#666',
            borderColor: '#AAA'
        },
        outlineColor: '#CCC',
        maskFill: 'rgba(255,255,255,0.1)',
        series: {
            color: '#7798BF',
            lineColor: '#A6C7ED'
        },
        xAxis: {
            gridLineColor: '#505053'
        }
    },

    scrollbar: {
        barBackgroundColor: '#808083',
        barBorderColor: '#808083',
        buttonArrowColor: '#CCC',
        buttonBackgroundColor: '#606063',
        buttonBorderColor: '#606063',
        rifleColor: '#FFF',
        trackBackgroundColor: '#404043',
        trackBorderColor: '#404043'
    },

    // special colors for some of the
    legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
    background2: '#505053',
    dataLabelsColor: '#B0B0B3',
    textColor: '#C0C0C0',
    contrastTextColor: '#F0F0F3',
    maskColor: 'rgba(255,255,255,0.3)'
};

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

        this.chartComponent = React.createRef();

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
        this.setState({
            hsOption: {
                rangeSelector: {
                    selected: 2
                },
                title: {
                    text: self.state.stockCode
                },
                series: _hsSeries
            }
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
                zIndex: 2
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
                    data: upperBBand,
                    linkedTo: 'stock_price',
                    zIndex: 1,
                    marker: {
                        enabled: false
                    }
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
                    data: middleBBand,
                    linkedTo: 'stock_price',
                    zIndex: 1,
                    marker: {
                        enabled: false
                    }
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
                    data: lowerBBand,
                    linkedTo: 'stock_price',
                    zIndex: 1,
                    marker: {
                        enabled: false
                    }
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
                    data: smaValue,
                    id: 'sma',
                    zIndex: 1
                    , yAxis: 0
                }
            );
            console.log('Adding Simple Moving Average');
        }

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
      <StockChart
        highcharts={Highcharts}
        options={this.state.hsOption}
      />
    </Col>
  </Row>
</Container>
        );
    }
}

// Default exported component
export default StockMgrDemoApp;