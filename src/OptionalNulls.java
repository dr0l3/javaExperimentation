import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by g50848 on 26/06/2017.
 */
public class OptionalNulls {
    public static void main(String[] args) {
        Optional<String> none = Stream.of("hello", "world").filter(v -> v.contains("lkajsdlkajsdlkja")).findFirst();
        String hehe = none.map(s -> s + "hehhe").orElse(null);
        System.out.println(hehe); //works fine, prints null

        Optional<Boolean> none2 = Stream.of(false,false).filter(v -> v).findFirst();
        String heheh2 = none2.map(v -> v.toString()).orElse(null);
        System.out.println(heheh2); //works fine, print null

        boolean v = none2.map(v1 -> !v1)
                .orElse(null); //this throws a null pointer
        System.out.println(v);

        boolean v2 = none2.map(v1 -> !v1).get(); //no such element exception
        System.out.println(v2);

        Boolean v3 = none2.isPresent() ? none2.get() : null;
        System.out.println(v3); //prints null

        Boolean v4 = none2.orElse(null);
        System.out.println(v4); //prints null

    }
}
