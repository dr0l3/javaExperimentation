import java.util.stream.Stream;

/**
 * Created by g50848 on 13/06/2017.
 */
public class StreamAndNull {
    public static void main(String[] args) {
        String[] hello = null;
        Stream.of(hello)
                .filter(i -> true);
    }
}
