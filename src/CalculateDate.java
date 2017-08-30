import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculateDate {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate target = LocalDate.of(2017,07,05);
        LocalDate target2 = LocalDate.of(2017,8,28);
        System.out.println(DAYS.between(today,target));
        System.out.println(DAYS.between(today,target2));
    }
}
