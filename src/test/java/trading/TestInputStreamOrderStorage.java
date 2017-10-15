package trading;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;

public class TestInputStreamOrderStorage {

    @Before
    public void setUp() {
        Locale.setDefault(Locale.UK);
    }


    @Test
    public void testStorage() throws Exception {
        byte[] bytes = "bar,B,1.1,GBP,11 Oct 2017,11 Oct 2017,34030,100.25\n".getBytes();
        InputStreamOrderStorage storage = new InputStreamOrderStorage(new ByteArrayInputStream(bytes));
        Collection<Order> all = storage.findAll();
        Assert.assertEquals(1, all.size());
        Order order = all.iterator().next();
        Assert.assertEquals("bar", order.getEntity());
        Assert.assertEquals(BuySell.B, order.getSide());
        Assert.assertEquals(new BigDecimal("1.1"), order.getAgreedFx());
        Assert.assertEquals(LocalDate.of(2017, 10, 11), order.getInstructionDate());
        Assert.assertEquals(LocalDate.of(2017, 10, 11), order.getSettlementDate());
        Assert.assertEquals(new BigDecimal(34030), order.getUnits());
        Assert.assertEquals(new BigDecimal("100.25"), order.getPrice());


    }
}
