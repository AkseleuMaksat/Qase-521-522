package kz.Akseleu;
import org.junit.jupiter.api.Test;

public class UmagTest extends BaseTest {
    public final static String BASE_URL = "https://dev-web.umag.kz";
    public final static String LOGIN = "+7773939393";
    public final static String PASSWORD = "Gamu2024!";

    @Test
    public void checkHref(){
        MainPage mainPage = new MainPage(BASE_URL);
        mainPage.authorization(LOGIN, PASSWORD);

        SecondPage secondPage = new SecondPage();
        secondPage.openProductsPage();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.documentStatus();
        registrationPage.selectUser();

    }

}
