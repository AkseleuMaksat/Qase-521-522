package kz.Akseleu;

import io.qameta.allure.Allure;
import kz.Akseleu.pages.Authorization;
import kz.Akseleu.pages.RegistrationPage;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$x;

public class UmagTest extends BaseTest {
    public final static String BASE_URL = "https://dev-web.umag.kz";
    public final static String LOGIN = "+7773939393";
    public final static String PASSWORD = "Gamu2024!";
    public final static String FULLNAME = "Миша Понкратов";
    public final static String USERNAME = "Миша";
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Test
    public void checkHref() {
        Allure.step("Авторизуемся и захоидм в \"Списание\"", () -> {
            Authorization auth = new Authorization(BASE_URL);
            auth.authorization(LOGIN, PASSWORD);
            auth.openProductsPage();
        });
        Allure.step("Проверяем статус документа и выбираем пользователя", () -> {
            RegistrationPage registrationPage = new RegistrationPage();
            Allure.step("Нажать на кнопку \"Фильтр\"", () -> {
                registrationPage.filterButton();
            });
            Allure.step("Нажать на таб \"Месяц\"", () -> {
               registrationPage.monthButton();
            });
            Allure.step("Нажать на дропдаун \"Статус документа\"", subStep -> {
                registrationPage.statusDocument();
                subStep.parameter("Выбрать чек-бокс","Удален");
                registrationPage.deletedCheckBox();

            });
            Allure.step("Нажать на дропдаун \"Пользователи\"", subStep -> {
                registrationPage.userDropdown();
                subStep.parameter("В поле поиска ввести имя", FULLNAME);
                subStep.parameter("Выбрать пользователя ", FULLNAME);
                registrationPage.selectUser(FULLNAME);
            });
            Allure.step("Нажать на кнопку \"Применить\"", subStep -> {
                registrationPage.buttonSummit();
                subStep.parameter("Проверить колонку", "Дата");
                TableCheck tableCheck = new TableCheck();
                tableCheck.checkDate();
                subStep.parameter("Проверить колонку", "Статус");
                tableCheck.checkStatus();
                subStep.parameter("Проверить колонку","Пользователи");
                tableCheck.checkName();
                subStep.parameter("Проверить","Чипсы ");
                ChipsCheck chipsCheck = new ChipsCheck();
                chipsCheck.checkChip(USERNAME,"Удален");
            });

        });
    }
}
