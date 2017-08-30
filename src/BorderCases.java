import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by g50848 on 01/03/2017.
 */
public class BorderCases {
    public static void main(String[] args) {
        printExample("");
        System.out.println("-----------------");
        printExample(" ");
        System.out.println("-----------------");
        printExample(",");
    }

    public static void printExample(String example){
        Optional<List<String>> maybe = Optional.ofNullable(example)
                .map(t -> t.replace(" ", ""))
                .map(t -> Arrays.asList(t.split(",")));
        //print true if tags
        System.out.println(maybe.isPresent());

        String[] old = example.replace(" ", "").split(",");
        //print true tags
        System.out.println(!(old.length == 1 && old[0].isEmpty()));
    }
}
