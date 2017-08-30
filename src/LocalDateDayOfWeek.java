import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class LocalDateDayOfWeek {
    public static void main(String[] args) {
        LocalDate monday = LocalDate.of(2017,8,22) ;
        LocalDate tuesday = LocalDate.of(2017,8,23) ;
        LocalDate wednesday = LocalDate.of(2017,8,24) ;
        LocalDate thursday = LocalDate.of(2017,8,25);
        LocalDate friday = LocalDate.of(2017,8,26);
        LocalDate saturday = LocalDate.of(2017,8,27);
        LocalDate sunday = LocalDate.of(2017,8,28);
        LocalDate monday2 = LocalDate.of(2017,8,29) ;



        List<LocalDate> dates = Arrays.asList(monday,tuesday,wednesday,thursday,friday,saturday,sunday, monday2);
        dates.stream().map(v -> subtractExcludingWeekend(v)).forEach(d -> System.out.println(d));


    }

    public static LocalDate subtractExcludingWeekend(LocalDate original) {
        int toSubtract;
        switch (original.getDayOfWeek()) {
            case MONDAY: toSubtract = 3; break;
            case SUNDAY: toSubtract = 2; break;
            default: toSubtract = 1; break;
        }
        return original.minusDays(toSubtract);
    }
}
