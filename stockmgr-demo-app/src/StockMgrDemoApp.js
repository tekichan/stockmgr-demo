import React from 'react';
import { Component } from 'react';

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'

const options = {
  title: {
    text: 'My chart'
  },
  series: [{
    data: [1, 2, 3]
  }]
}

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
    }

    /**
     * React JS Render function
     */
    render() {
        return (
<Container>
  <Row>
    <Col>
      <Form.Control placeholder="Stock Code" />
    </Col>
    <Col>
      <Form.Control as="select">
        <option>SMA 20</option>
        <option>SMA 100</option>
        <option>SMA 250</option>
        <option>BBand 20</option>
      </Form.Control>
    </Col>
    <Col>
       <Button variant="primary" type="submit">Refresh</Button>
    </Col>
  </Row>
  <Row>
    <Col>
      <HighchartsReact
        highcharts={Highcharts}
        options={options}
      />
    </Col>
  </Row>
</Container>
        );
    }
}

// Default exported component
export default StockMgrDemoApp;