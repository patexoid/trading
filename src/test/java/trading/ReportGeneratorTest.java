package trading;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ReportGeneratorTest {

    @Before
    public void setUp() {
        Locale.setDefault(Locale.UK);
    }


    @Test
    public void testSingleOrderGenerator(){
        ReportGenerator reportGenerator = new ReportGenerator();
        List<CSVOrder> csvOrders = Collections.singletonList(new CSVOrder("boo,B,1,USD,01 May 2017,01 May 2017,1,100.25"));
        List<String> result=new ArrayList<>();
        reportGenerator.generateReport(csvOrders, result::add,() -> {},() -> {});
        Assert.assertEquals(2,result.size());
        Assert.assertEquals("B\t2017-05-01\t100.25",result.get(0));
        Assert.assertEquals("B\tboo\t100.25",result.get(1));
    }

    @Test
    public void testMultipleOrderGenerator(){
        ReportGenerator reportGenerator = new ReportGenerator();
        List<CSVOrder> csvOrders = Arrays.asList(
                new CSVOrder("boo,B,1,USD,01 May 2017,01 May 2017,1,100.25"),
                new CSVOrder("boo,B,1,USD,01 May 2017,01 May 2017,1,100.25"),
                new CSVOrder("boo,S,1,USD,01 May 2017,01 May 2017,1,100.25"),
                new CSVOrder("boo,B,1,USD,01 May 2017,02 May 2017,1,100.25"),
                new CSVOrder("bar,B,1,USD,01 May 2017,01 May 2017,1,100.25"));
        List<String> result=new ArrayList<>();
        reportGenerator.generateReport(csvOrders, result::add,() -> {},() -> {});
        Assert.assertEquals(6,result.size());
        Assert.assertEquals("B\t2017-05-01\t300.75",result.get(0));
        Assert.assertEquals("B\t2017-05-02\t100.25",result.get(1));
        Assert.assertEquals("S\t2017-05-01\t100.25",result.get(2));
        Assert.assertEquals("B\tboo\t300.75",result.get(3));
        Assert.assertEquals("B\tbar\t100.25",result.get(4));
        Assert.assertEquals("S\tboo\t100.25",result.get(5));


    }
}
