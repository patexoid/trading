package trading;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Order interface implementation, to simplify order creation from csv line
 */
public class CSVOrder implements Order {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private final String entity;
    private final BuySell buySell;
    private final BigDecimal agreedFx;
    private final Currency currency;
    private final LocalDate instructionDate;
    private final LocalDate settlementDate;
    private final BigDecimal units;
    private final BigDecimal price;

    public CSVOrder(String line) {
        String[] split = line.split(",");
        entity = split[0];
        buySell = BuySell.valueOf(split[1]);
        agreedFx = new BigDecimal(split[2]);
        currency = Currency.getCurrency(split[3]);
        instructionDate = LocalDate.parse(split[4], DATE_FORMAT);
        settlementDate = LocalDate.parse(split[5], DATE_FORMAT);
        units = new BigDecimal(split[6]);
        price = new BigDecimal(split[7]);
    }


    @Override
    public String getEntity() {
        return entity;
    }

    @Override
    public BuySell getSide() {
        return buySell;
    }

    @Override
    public BigDecimal getAgreedFx() {
        return agreedFx;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    @Override
    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    @Override
    public BigDecimal getUnits() {
        return units;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}
