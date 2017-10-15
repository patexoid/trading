package trading;

import java.util.Collection;

/**
 * Order Storage interface can have multiple implementations
 */
public interface OrderStorage {

    Collection<Order> findAll();
}
