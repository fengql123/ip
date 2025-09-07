package kris.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT = DateTimeFormatter
            .ofPattern("MMM dd yyyy HHmm'hrs'");

    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        // Try different date formats
        DateTimeFormatter[] formats = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"), // 2019-12-02
                DateTimeFormatter.ofPattern("d/M/yyyy"), // 2/12/2019
                DateTimeFormatter.ofPattern("dd/MM/yyyy"), // 02/12/2019
                DateTimeFormatter.ofPattern("d-M-yyyy"), // 2-12-2019
                DateTimeFormatter.ofPattern("dd-MM-yyyy") // 02-12-2019
        };

        for (DateTimeFormatter format : formats) {
            try {
                return LocalDate.parse(dateString.trim(), format);
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        return null; // Unable to parse
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        // Try different datetime formats first
        DateTimeFormatter[] dateTimeFormats = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"), // 2019-12-02 1800
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"), // 2/12/2019 1800
                DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"), // 02/12/2019 1800
                DateTimeFormatter.ofPattern("d-M-yyyy HHmm"), // 2-12-2019 1800
                DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm") // 02-12-2019 1800
        };

        for (DateTimeFormatter format : dateTimeFormats) {
            try {
                return LocalDateTime.parse(dateTimeString.trim(), format);
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }

        // If no time found, try parsing as date only
        LocalDate date = parseDate(dateTimeString);
        if (date != null) {
            return date.atStartOfDay();
        }

        return null; // Unable to parse
    }

    public static boolean hasTime(String input) {
        // Simple check to see if input contains time (4 digits in a row)
        return input != null && input.matches(".*\\d{4}.*");
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(OUTPUT_DATE_FORMAT);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        // If time is start of day (00:00), show date only
        if (dateTime.getHour() == 0 && dateTime.getMinute() == 0) {
            return dateTime.format(OUTPUT_DATE_FORMAT);
        }

        return dateTime.format(OUTPUT_DATETIME_FORMAT);
    }

    public static boolean isValidDate(String dateString) {
        return parseDate(dateString) != null;
    }

    public static boolean isValidDateTime(String dateTimeString) {
        return parseDateTime(dateTimeString) != null;
    }
}
