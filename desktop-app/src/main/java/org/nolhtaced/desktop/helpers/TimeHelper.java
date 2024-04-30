package org.nolhtaced.desktop.helpers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeHelper {
    public static List<LocalTime> TIME_INTERVALS = new ArrayList<>();

    static {
        for (int i = 9; i < 20; i++) {
            for (int j = 0; j < 46; j = j + 15) {
                TIME_INTERVALS.add(LocalTime.of(i, j));
            }
        }
    }
}
