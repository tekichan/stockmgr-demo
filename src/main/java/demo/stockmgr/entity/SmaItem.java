package demo.stockmgr.entity;

import java.time.ZonedDateTime;

/**
 * Item Entity of Simple Moving Average
 * @author Teki
 * @since 30 May 2019
 */
public class SmaItem {
    private String symbol;
    private Integer timeFrame;
    private ZonedDateTime dateTime;
    private Double smaValue;

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

    public Double getSmaValue() {
        return smaValue;
    }

    public void setSmaValue(Double smaValue) {
        this.smaValue = smaValue;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
