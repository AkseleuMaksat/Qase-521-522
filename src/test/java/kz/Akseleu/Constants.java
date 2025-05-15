package kz.Akseleu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constants {
    public final static String BASE_URL = "https://dev-web.umag.kz";
    public final static String LOGIN = "+7773939393";
    public final static String PASSWORD = "Gamu2024!";
    public final static String FULLNAME = "Миша Понкратов";
    public final static String USERNAME = "Миша";
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final LocalDate NOW = LocalDate.now();
    public final static LocalDate START_DATE = NOW.minusDays(NOW.getDayOfMonth() - 1);
    public final static LocalDate END_DATE = NOW.plusDays(NOW.lengthOfMonth() - NOW.getDayOfMonth());

}
