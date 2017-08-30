import java.util.*;

/**
 * Created by g50848 on 05/04/2017.
 */
public class StreamAndMaps {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("hello", "world", "this", "is", "dog", "dog");
        Map<String, Integer> mapping = new HashMap<>();
        Random random = new Random(1);
        printMap(mapping);
        stringList.forEach(s -> mapping.computeIfAbsent(s, hehe -> {
            Integer insert = random.nextInt(10);
            System.out.println("To be inserted: " + insert);
            return insert;}));
        printMap(mapping);
    }

    public static void printMap(Map<String, Integer> map){
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

}
