package kz.Akseleu.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Constants {
    public final static String BASE_URL = "https://dev-web.umag.kz";
    public final static String LOGIN = "+7773939393";
    public final static String PASSWORD = "Gamu2024!";
    public final static String FULL_NAME = "Миша Понкратов";
    public final static String USERNAME = "Миша";
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public final static String BARCODE = "Autotest";
    public static final LocalDate NOW = LocalDate.now();

    public final static LocalDate START_CURRENT_MONTH = NOW.minusDays(NOW.getDayOfMonth() - 1);
    public final static LocalDate END_CURRENT_MONTH = NOW.plusDays(NOW.lengthOfMonth() - NOW.getDayOfMonth());

    public final static LocalDate START_CURRENT_WEEK = NOW.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek());
    public final static LocalDate END_CURRENT_WEEK = START_CURRENT_WEEK.plusDays(6);
}
