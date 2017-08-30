import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by g50848 on 31/03/2017.
 */
public class DateTimeForamt {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDateTime todayAtSomeTIme = today.atTime(23,59,59);
        ZonedDateTime zone = todayAtSomeTIme.atZone(ZoneId.systemDefault());
        String res = zone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println(res);
        System.out.println(today.toString());
        System.out.println(today.format(DateTimeFormatter.ISO_DATE));
    }
}
