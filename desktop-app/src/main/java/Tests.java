import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tests {

    public static void main(String[] args) {
        List<LocalTime> TIME_INTERVALS = new ArrayList<>();

        Instant ins = Instant.now();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MM-dd H:m").withZone(ZoneId.of("UTC"));

        System.out.println(format.format(ins));


        for (int i = 9; i < 20; i++) {
            for (int j = 0; j < 46; j = j + 15) {
                TIME_INTERVALS.add(LocalTime.of(i, j));
            }
        }

    }

}
