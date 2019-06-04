package demo.stockmgr.entity;

import java.time.ZonedDateTime;

/**
 * Item Entity of Bollinger indicator
 * @author Teki
 * @since 4 Jun 2019
 */
public class BollingerItem {
    private String symbol;
    private Integer timeFrame;
    private ZonedDateTime dateTime;
    private Double upperBBand;
    private Double middleBBand;
    private Double lowerBBand;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(Integer timeFrame) {
        this.timeFrame = timeFrame;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getUpperBBand() {
        return upperBBand;
    }

    public void setUpperBBand(Double upperBBand) {
        this.upperBBand = upperBBand;
    }

    public Double getMiddleBBand() {
        return middleBBand;
    }

    public void setMiddleBBand(Double middleBBand) {
        this.middleBBand = middleBBand;
    }

    public Double getLowerBBand() {
        return lowerBBand;
    }

    public void setLowerBBand(Double lowerBBand) {
        this.lowerBBand = lowerBBand;
    }
}
