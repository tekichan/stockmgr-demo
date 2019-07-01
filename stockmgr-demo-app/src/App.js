import React from 'react';

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'

import './App.css';

const options = {
  title: {
    text: 'My chart'
  },
  series: [{
    data: [1, 2, 3]
  }]
}

// Main App
function App() {
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

export default App;
