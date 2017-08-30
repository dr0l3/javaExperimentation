import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by g50848 on 10/04/2017.
 */
public class StreamMergeMaps {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Hello", 1);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("World!", 2);

        Map<String,Integer> mergedMaps = Stream.of(map, map2)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (first, last) -> first));

        System.out.println(mergedMaps);
    }
}
