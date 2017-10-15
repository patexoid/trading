package trading;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Order interface can have multiple implementations
 */
public interface Order {
    String getEntity();

    BuySell getSide();

    BigDecimal getAgreedFx();

    Currency getCurrency();

    LocalDate getInstructionDate();

    LocalDate getSettlementDate();

    BigDecimal getUnits();

    BigDecimal getPrice();
}
