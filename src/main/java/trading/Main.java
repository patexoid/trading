package trading;

import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;

public class Main {


    public static void main(String[] args) throws Exception{

        Locale.setDefault(Locale.UK);// Issues with my default locale

        InputStream resourceAsStream = Main.class.getResourceAsStream("/trading.csv");
        OrderStorage inputStreamOrderStorage = new InputStreamOrderStorage(resourceAsStream);
        Collection<Order> all = inputStreamOrderStorage.findAll();
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReport(all,
                System.out::println,
                () -> System.out.println("\nSide\tDate\tValue"),
                () -> System.out.println("\nSide\tEntity\tValue")
                );
    }
}
