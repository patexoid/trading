package trading;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TestCurrency {


    public static final LocalDate MONDAY = LocalDate.of(2017, 5, 1);
    public static final LocalDate TUESDAY = LocalDate.of(2017,5, 2);
    public static final LocalDate WEDNESDAY = LocalDate.of(2017, 5, 3);
    public static final LocalDate THURSDAY = LocalDate.of(2017, 5, 4);
    public static final LocalDate FRIDAY = LocalDate.of(2017, 5, 5);
    public static final LocalDate SATURAY = LocalDate.of(2017, 5, 6);
    public static final LocalDate SUNDAY = LocalDate.of(2017, 5, 7);
    public static final LocalDate NEXT_MONDAY = LocalDate.of(2017, 5, 8);
    public static final LocalDate NEXT_TUESDAY = LocalDate.of(2017, 5, 9);

    @Test
    public void testWorkDayNormalOrder(){
        Currency currency = new Currency(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY);
        Assert.assertEquals(TUESDAY,currency.findTradeDate(MONDAY));
        Assert.assertEquals(TUESDAY,currency.findTradeDate(TUESDAY));
        Assert.assertEquals(WEDNESDAY,currency.findTradeDate(WEDNESDAY));
        Assert.assertEquals(THURSDAY,currency.findTradeDate(THURSDAY));
        Assert.assertEquals(FRIDAY,currency.findTradeDate(FRIDAY));
        Assert.assertEquals(NEXT_TUESDAY,currency.findTradeDate(SATURAY));
        Assert.assertEquals(NEXT_TUESDAY,currency.findTradeDate(SUNDAY));
    }

    @Test
    public void testWorkDayReverseOrder(){
        Currency currency = new Currency(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);
        Assert.assertEquals(MONDAY,currency.findTradeDate(MONDAY));
        Assert.assertEquals(TUESDAY,currency.findTradeDate(TUESDAY));
        Assert.assertEquals(WEDNESDAY,currency.findTradeDate(WEDNESDAY));
        Assert.assertEquals(THURSDAY,currency.findTradeDate(THURSDAY));
        Assert.assertEquals(SUNDAY,currency.findTradeDate(FRIDAY));
        Assert.assertEquals(SUNDAY,currency.findTradeDate(SATURAY));
        Assert.assertEquals(SUNDAY,currency.findTradeDate(SUNDAY));
    }

}
