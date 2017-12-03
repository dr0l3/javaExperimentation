import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNUmbers {
    public static void main(String[] args) {
        double min = Double.MIN_VALUE;
        double max = Double.MAX_VALUE;
        System.out.println(min < -10);
        System.out.println(max> 10);
        System.out.println(-max < -10);
        //what the fuck is this??
        for (int i = 0; i < 20; i++) {
            System.out.println(ThreadLocalRandom.current().nextDouble(-(max), max));
        }

        Random r = new Random();
        System.out.println("---");
        System.out.println((max-1)-(-max));
        for (int i = 0; i < 20; i++) {
            System.out.println(min + (max - min) * r.nextDouble());
        }
    }
}
