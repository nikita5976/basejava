package webapp.util;

import webapp.model.Company;

public class HtmlUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Company.Period period) {
        return DateUtil.format(period.getDateStart()) + " - " + DateUtil.format(period.getDateEnd());
    }
}
