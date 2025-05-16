package kz.Akseleu.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Constants {
    public final static String BASE_URL = "https://dev-web.umag.kz";
    public final static String LOGIN = "+7773939393";
    public final static String PASSWORD = "Gamu2024!";
    public final static String FULL_NAME = "Миша Понкратов";
    public final static String USERNAME = "Миша";
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final LocalDate NOW = LocalDate.now();
    public final static LocalDate START_CURRENT_MONTH = NOW.minusDays(NOW.getDayOfMonth() - 1);
    public final static LocalDate END_CURRENT_MONTH = NOW.plusDays(NOW.lengthOfMonth() - NOW.getDayOfMonth());

}
