package trading;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Currency class, contains working days info
 * <p>
 * <p>
 * Main idea is to represent first work day, as first week day
 */
public class Currency {

    private final static Map<String, Currency> CURRENCIES;

    private final static Currency defaultCurrency = new Currency(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);

    static {
        Map<String, Currency> currencies = new HashMap<>();
        currencies.put("AED", new Currency(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY));
        currencies.put("SAR", new Currency(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY));
        CURRENCIES = Collections.unmodifiableMap(currencies);
    }

    private int workWeekDuration;
    private int coef;


    Currency(DayOfWeek weekStart, DayOfWeek weekEnd) {
        int weekStartI = weekStart.getValue();
        int weekEndI = weekEnd.getValue();
        this.coef = weekStartI;
        this.workWeekDuration = (weekEndI - coef + 7) % 7;
    }

    public static Currency getCurrency(String s) {
        return CURRENCIES.getOrDefault(s, defaultCurrency);
    }

    /**
     * Find nearest possible trade date for currency
     *
     * @param date desired trade date
     * @return nearest possible trade date
     */
    public LocalDate findTradeDate(LocalDate date) {
        int value = date.getDayOfWeek().getValue();
        int dayOfWeek = (value - coef + 7) % 7;
        if (dayOfWeek <= workWeekDuration) {
            return date;
        } else {
            return date.plusDays(7 - dayOfWeek);
        }
    }
}
