import java.util.UUID;

/**
 * Created by g50848 on 27/02/2017.
 */
public class Uuid {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }
}
