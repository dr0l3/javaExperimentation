public class StringFormatting {
    public static void main(String[] args) {
        String example1 = String.format("Hello from %s, this is %s", "the void", "dog");
        System.out.println(example1);
        String example2 = String.format("%d = %s <-> %s = %d",2,"f","f",2);
        System.out.println(example2);
//        String example2 = String.format("%d = %s <-> %s = %d","f",2, 2,"f"); <-- throws exception, arguments in wrong order

    }
}
