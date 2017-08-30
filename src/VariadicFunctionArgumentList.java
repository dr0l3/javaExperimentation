import java.util.Arrays;

/**
 * Created by g50848 on 20/06/2017.
 */
public class VariadicFunctionArgumentList {
    public static void main(String[] args) {
        totalLength("hello");
        totalLength("hello", "world");
    }

    public static int totalLength(String... words){
        System.out.println(Arrays.toString(words));
        for (String w :words) {
            System.out.println(w);
        }
        return 1;
    }
}
