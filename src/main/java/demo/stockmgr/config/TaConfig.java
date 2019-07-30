package demo.stockmgr.config;

/**
 * Configuration for Technical Analysis
 * @author Teki Chan
 * @since 30 May 2019
 */
public class TaConfig {
    private Integer defaultTimeframe;

    /**
     * Getter of Default Time Frame value
     * @return  Default Time Frame value
     */
    public Integer getDefaultTimeframe() {
        return defaultTimeframe;
    }

    /**
     * Setter of Default Time Frame value
     * @param defaultTimeframe  Default Time Frame value
     */
    public void setDefaultTimeframe(Integer defaultTimeframe) {
        this.defaultTimeframe = defaultTimeframe;
    }
}
