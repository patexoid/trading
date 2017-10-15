package trading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Simple order storage
 */
public class InputStreamOrderStorage implements OrderStorage {

    private final Collection<Order> orders;


    public InputStreamOrderStorage(InputStream is) throws IOException {
        Collection<Order> orders= new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line = reader.readLine();
            while (line != null) {
                orders.add(new CSVOrder(line));
                line = reader.readLine();
            }
        }
        this.orders= Collections.unmodifiableCollection(orders);
    }

    @Override
    public Collection<Order> findAll() {
        return orders;
    }
}
