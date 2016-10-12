package seedu.address.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
     * Parses a String into a Date
     * 
     * @throws ParseException
     */
    public static Date parseStringToDate(String strDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        Date date = df.parse(strDate);
        return date;
    }
}
