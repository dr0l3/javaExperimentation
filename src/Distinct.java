import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by g50848 on 02/03/2017.
 */
public class Distinct {
    public static void main(String[] args) {
        List<Double> doubles = new ArrayList<>();
        doubles.add(1d);
        doubles.add(1d);
        doubles.add(0.1d);
        System.out.println(doubles.stream().distinct().count()); //prints 2
        System.out.println(doubles.stream().distinct().collect(Collectors.toList()));
        System.out.println(doubles.stream().count()); //prints 2

        List<String> strings = Arrays.asList("Hello", "Hello", "World");
        System.out.println(strings.stream().distinct().count()); //prints 2
        System.out.println(strings.stream().distinct().collect(Collectors.toList()));
        System.out.println(strings.stream().count()); //prints 2
    }
}
