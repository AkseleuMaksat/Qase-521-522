package kz.Akseleu;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;

public class UmagTest extends BaseTest {
    public final static String BASE_URL = "https://dev-web.umag.kz";
    public final static String LOGIN = "+7773939393";
    public final static String PASSWORD = "Gamu2024!";

    @Test
    public void checkHref() {
        Allure.step("Открываем главную страницу и авторизуемся", () -> {
            MainPage mainPage = new MainPage(BASE_URL);
            mainPage.authorization(LOGIN, PASSWORD);
        });

        Allure.step("Открываем страницу продуктов", () -> {
            SecondPage secondPage = new SecondPage();
            secondPage.openProductsPage();
        });

        Allure.step("Проверяем статус документа и выбираем пользователя", () -> {
            RegistrationPage registrationPage = new RegistrationPage();
            registrationPage.documentStatus();
            registrationPage.selectUser();
        });
    }
}
