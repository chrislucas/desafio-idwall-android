package xplorer.br.com.apiidwall.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HelperDateFormat {

    public static final String DEFAULT_BR_FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";

    public static String convertFormatDateToOtherFormatDate(String date, String oldFormat, String newFormat) {
        long t = convertFormatDateToMilliseconds(oldFormat, date);
        return convertLongToDateFormat(t, newFormat);
    }

    public static long convertFormatDateToMilliseconds(String currentFormat, String dateFormatted) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(currentFormat, Locale.getDefault());
        long milliseconds = -1;
        try {
            Date date = dateFormat.parse(dateFormatted);
            return date.getTime();
        }
        catch (ParseException e) {
            Log.e("EXCP_CVT_FTM_TO_DATE", e.getMessage());
        }
        return milliseconds;
    }

    public static String convertLongToDateFormat(long date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(new Date(date));
    }
}
