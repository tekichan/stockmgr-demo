package demo.stockmgr.entity;

import java.util.Calendar;

/**
 * Item Entity of Simple Moving Average
 * @author Teki
 * @since 30 May 2019
 */
public class SmaItem {
    private String symbol;
    private Integer timeFrame;
    private Calendar date;
    private Double smaValue;

    /**
     * Getter of Stock Code Symbol
     * @return  Stock Code Symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Setter of Stock Code Symbol
     * @param symbol    Stock Code Symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter of SMA Time Frame
     * @return  SMA Time Frame
     */
    public Integer getTimeFrame() {
        return timeFrame;
    }

    /**
     * Setter of SMA Time Frame
     * @param timeFrame SMA Time Frame
     */
    public void setTimeFrame(Integer timeFrame) {
        this.timeFrame = timeFrame;
    }

    /**
     * Getter of SMA value
     * @return  SMA value
     */
    public Double getSmaValue() {
        return smaValue;
    }

    /**
     * Setter of SMA value
     * @param smaValue
     */
    public void setSmaValue(Double smaValue) {
        this.smaValue = smaValue;
    }

    /**
     * Getter of Transaction Date
     * @return  Transaction Date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * Setter of Transaction Date
     * @param date  Transaction Date
     */
    public void setDate(Calendar date) {
        this.date = date;
    }
}
