import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ComposingNumericPredicates {
    public static void main(String[] args) {
        Predicate<Number> alwaysTrue = (v) -> true;
        Predicate<Number> nonNeg = (v) -> v.floatValue() > -1f;
        Predicate<Number> isEven = (v) -> v.floatValue() % 2 == 0;

        List<Predicate<Number>> pres = new ArrayList<>();
        List<Integer> ints = Arrays.asList(-1,2,0,10,15);
        pres.add(nonNeg);
        pres.add(isEven);

        Predicate<Number> total = alwaysTrue.and(nonNeg).and(isEven);
        Predicate<Number> totalReduce = pres.stream().reduce(alwaysTrue, Predicate::and);
        ints.stream().filter(totalReduce::test).forEach(System.out::println);
        ints.stream().filter(total::test).forEach(System.out::println);
    }
}
