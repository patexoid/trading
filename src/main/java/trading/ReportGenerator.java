package trading;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Simple Report generator
 */
public class ReportGenerator {

    /**
     * generate report according to requirement int TSV format
     *
     * @param orders List of orders
     * @param reportWriter Consume line of report
     * @param rankingHeader buySellHeader ranking header
     */
    public <T extends Order> void generateReport(Collection<T> orders, Consumer<String> reportWriter,
                                                 Runnable buySellHeader, Runnable rankingHeader) {
        /*
          In case if performance is required here code above can be replaced with single foreach and two sort
         */

        Function<Order, LocalDate> getTradeDate = o -> o.getCurrency().findTradeDate(o.getSettlementDate());

        Map<ComparableTuple<BuySell, LocalDate>, MutableUSDValue> buysSellReport = orders.stream().
                map(order -> new Tuple<>(createKey(order, getTradeDate), getUSDValue(order))).
                collect(Collectors.groupingBy(Tuple::_1,
                        Collector.of(MutableUSDValue::new, (usdVal, o) -> usdVal.addToThis(o._2),
                                MutableUSDValue::add)));

        buySellHeader.run();
        buysSellReport.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> {
                    Tuple<BuySell, LocalDate> key = entry.getKey();
                    reportWriter.accept(key._1 + "\t" + key._2 + "\t" + entry.getValue().getValue());
                }
        );

        rankingHeader.run();
        Map<ComparableTuple<BuySell, String>, MutableUSDValue> rankingReport = orders.stream().
                map(order -> new Tuple<>(createKey(order, Order::getEntity), getUSDValue(order))).
                collect(Collectors.groupingBy(Tuple::_1,
                        Collector.of(MutableUSDValue::new, (usdVal, o) -> usdVal.addToThis(o._2),
                                MutableUSDValue::add)));


        rankingReport.entrySet().stream().
                sorted(Comparator.comparing(e -> e.getValue().getValue().negate())).forEach(entry -> {
                    Tuple<BuySell, String> key = entry.getKey();
                    reportWriter.accept(key._1 + "\t" + key._2 + "\t" + entry.getValue().getValue());
                }
        );


    }

    private <T extends Comparable<? super T>> ComparableTuple<BuySell, T> createKey(Order order, Function<Order, T> f) {
        return new ComparableTuple<>(order.getSide(), f.apply(order));
    }

    private MutableUSDValue getUSDValue(Order order) {
        return new MutableUSDValue(order.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).
                multiply(order.getUnits()).
                multiply(order.getAgreedFx()));
    }


    /**
     * Lets do some scala style
     *
     * @param <F>
     * @param <S>
     */
    class Tuple<F, S> {
        final F _1;
        final S _2;

        Tuple(F _1, S _2) {
            this._1 = _1;
            this._2 = _2;
        }

        F _1() {
            return _1;
        }

        S _2() {
            return _2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Tuple<?, ?> tuple = (Tuple<?, ?>) o;

            return _1.equals(tuple._1) && _2.equals(tuple._2);
        }

        @Override
        public int hashCode() {
            int result = _1.hashCode();
            result = 31 * result + _2.hashCode();
            return result;
        }

    }

    class ComparableTuple<F extends Comparable<? super F>, S extends Comparable<? super S>> extends Tuple<F, S>
            implements Comparable<ComparableTuple<F, S>> {

        ComparableTuple(F _1, S _2) {
            super(_1, _2);
        }

        @Override
        public int compareTo(ComparableTuple<F, S> o) {
            int first = _1.compareTo(o._1);
            if (first == 0) {
                return _2.compareTo(o._2);
            } else {
                return first;
            }
        }

    }


    class MutableUSDValue {
        BigDecimal value = new BigDecimal(0);

        MutableUSDValue() {
        }

        MutableUSDValue(BigDecimal value) {
            addToThis(value);
        }

        void addToThis(BigDecimal value) {
            this.value = this.value.add(value);
        }

        void addToThis(MutableUSDValue value) {
            addToThis(value.getValue());
        }

        MutableUSDValue add(MutableUSDValue value) {
            return new MutableUSDValue(value.getValue().add(getValue()));
        }

        BigDecimal getValue() {
            return value;
        }
    }

}
