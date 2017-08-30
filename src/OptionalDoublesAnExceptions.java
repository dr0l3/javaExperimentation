import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Function;

/**
 * Created by g50848 on 05/04/2017.
 */
public class OptionalDoublesAnExceptions {
    public static void main(String[] args) {
        Function<String, Double> someFunction = input -> 1d;
        if(someFunction.apply("hehe") == null){
            System.out.println("OMG IT IS ALL BAD");
        }
        double something = Optional.ofNullable(someFunction.apply("hehe")).orElseThrow(() -> new RuntimeException("this is an exception"));
        System.out.println(something);
        //double value = OptionalDouble.of(someFunction.apply("hehe")).orElseThrow(() -> new RuntimeException("this is an exception"));
    }

    public static double someName(){
        return Double.parseDouble("12");
    }
}
