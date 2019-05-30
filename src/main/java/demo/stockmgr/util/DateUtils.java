package demo.stockmgr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Date related Utility Class
 * @since Teki Chan
 * @since 29 May 2019
 */
public final class DateUtils {
    /**
     * Get Calendar object from String Optional
     * @param yyyyMMddOptional  String Optional of Date
     * @param datePattern   Date Pattern
     * @param timeZone  Time Zone
     * @return  Calendar object or null if failed to parse
     * @throws ParseException
     */
    public static Calendar getDateFromOptional(Optional<String> yyyyMMddOptional, String datePattern, String timeZone) throws ParseException {
        if ((yyyyMMddOptional != null)
                && yyyyMMddOptional.isPresent()
                && (yyyyMMddOptional.get().length() == datePattern.length())
        ) {
            return getDateFromString(yyyyMMddOptional.get(), datePattern, timeZone);
        } else {
            return null;
        }
    }

    /**
     * Get Calendar object from String
     * @param yyyyMMddString    String of Date
     * @param datePattern   Date Pattern
     * @param timeZone  Time Zone
     * @return  Calendar object
     * @throws ParseException
     */
    public static Calendar getDateFromString(String yyyyMMddString, String datePattern, String timeZone) throws ParseException {
        Calendar calObject = (timeZone == null)?Calendar.getInstance():Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        sdf.setTimeZone(calObject.getTimeZone());
        calObject.setTime(sdf.parse(yyyyMMddString));

        // Set Hour, Minute, Second and Millisecond to 0 for Date part only
        calObject.set(Calendar.HOUR, 0);
        calObject.set(Calendar.MINUTE, 0);
        calObject.set(Calendar.SECOND, 0);
        calObject.set(Calendar.MILLISECOND, 0);

        return calObject;
    }

    /**
     * Convert Calendar object to ZonedDateTime object
     * @param cal   Calendar object
     * @return  ZonedDateTime object
     */
    public static ZonedDateTime getZonedDateTime(Calendar cal) {
        return ZonedDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
    }
}
