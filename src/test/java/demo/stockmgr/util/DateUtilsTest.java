package demo.stockmgr.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Calendar;

/**
 * Unit Test for DateUtils
 * @author Teki Chan
 * @since 30 May 2019
 */
public class DateUtilsTest {
    @Test
    public void testGetDateFromString() throws Exception {
        String yyyy = "2019";
        String mm = "05";
        String dd = "28";
        String datePattern = "yyyyMMdd";
        String timeZone = "HKT";
        Calendar cal = DateUtils.getDateFromString(yyyy+mm+dd, datePattern, timeZone);
        Assert.assertEquals(Integer.parseInt(yyyy), cal.get(Calendar.YEAR));
        Assert.assertEquals(Integer.parseInt(mm), cal.get(Calendar.MONTH) + 1);
        Assert.assertEquals(Integer.parseInt(dd), cal.get(Calendar.DATE));
    }

    @Test
    public void testGetZonedDateTime() throws Exception {
        Calendar cal = Calendar.getInstance();
        ZonedDateTime zdt = DateUtils.getZonedDateTime(cal);
        Assert.assertEquals(cal.get(Calendar.YEAR), zdt.getYear());
        Assert.assertEquals(cal.get(Calendar.MONTH) + 1, zdt.getMonthValue());
        Assert.assertEquals(cal.get(Calendar.DATE), zdt.getDayOfMonth());
    }
}
