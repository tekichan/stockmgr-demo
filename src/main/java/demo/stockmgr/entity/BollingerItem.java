package demo.stockmgr.entity;

import java.util.Calendar;

/**
 * Item Entity of Bollinger indicator
 * @author Teki
 * @since 4 Jun 2019
 */
public class BollingerItem {
    private String symbol;
    private Integer timeFrame;
    private Calendar date;
    private Double upperBBand;
    private Double middleBBand;
    private Double lowerBBand;

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
     * Getter of BBand Time Frame
     * @return  BBand Time Frame
     */
    public Integer getTimeFrame() {
        return timeFrame;
    }

    /**
     * Setter of BBand Time Frame
     * @param timeFrame
     */
    public void setTimeFrame(Integer timeFrame) {
        this.timeFrame = timeFrame;
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
     * @param date
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * Getter of Upper BBand value
     * @return  Upper BBand value
     */
    public Double getUpperBBand() {
        return upperBBand;
    }

    /**
     * Setter of Upper BBand value
     * @param upperBBand
     */
    public void setUpperBBand(Double upperBBand) {
        this.upperBBand = upperBBand;
    }

    /**
     * Getter of Middle BBand value
     * @return  Middle BBand value
     */
    public Double getMiddleBBand() {
        return middleBBand;
    }

    /**
     * Setter of Middle BBand value
     * @param middleBBand   Middle BBand value
     */
    public void setMiddleBBand(Double middleBBand) {
        this.middleBBand = middleBBand;
    }

    /**
     * Getter of Lower BBand value
     * @return  Lower BBand value
     */
    public Double getLowerBBand() {
        return lowerBBand;
    }

    /**
     * Setter of Lower BBand value
     * @param lowerBBand    Lower BBand value
     */
    public void setLowerBBand(Double lowerBBand) {
        this.lowerBBand = lowerBBand;
    }
}
