package com.example.usermanagement.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class TimeUtils {

    private TimeUtils() {}

    public static Date toDate(OffsetDateTime offsetDateTime) {
        Date date = null;
        if (offsetDateTime != null) {
            date = Date.from(offsetDateTime.toInstant());
        }
        return date;
    }
}
