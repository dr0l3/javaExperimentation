import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by g50848 on 07/04/2017.
 */
public class MapFromStream {
    public static void main(String[] args) {
        Random random = new Random();
        List<String> stringList = Arrays.asList("hello", "world", "this", "is", "dog");
        Map<String, String> mapping = stringList.stream().collect(Collectors.toMap(a -> a, b -> "heheh"));
        System.out.println(mapping.toString());
    }
}
