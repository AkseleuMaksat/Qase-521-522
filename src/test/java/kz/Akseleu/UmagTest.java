package kz.Akseleu;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import kz.Akseleu.enums.Status;
import kz.Akseleu.pages.Authorization;
import kz.Akseleu.pages.RegistrationPage;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UmagTest extends BaseTest {
    Helper helper = new Helper();
    ElementsCollection rows = $$x("//tbody//tr[contains(@class,'p-selectable-row')]");

    @Test
    public void checkHref() {
        RegistrationPage registrationPage = new RegistrationPage();
        step("Открыта страница \"Списание\"", () -> {
            Authorization auth = new Authorization(Constants.BASE_URL);
            auth.authorization(Constants.LOGIN, Constants.PASSWORD);
            auth.openProductsPage();
        });

        step("Нажать на кнопку \"Фильтр\"", () -> {
            step("Открывается модальное окно фильтра", () -> {
                registrationPage.filterButton();
            });
        });

        step("Нажать на таб \"Месяц\"", () -> {
            registrationPage.monthButton();
            step("В поле \"Дэйтскролл\" - отображаются даты текущего месяца", () -> {
                assertTrue(
                        (registrationPage.today().isEqual(registrationPage.startDay())
                                || registrationPage.today().isAfter(registrationPage.startDay()))
                                && (registrationPage.today().isEqual(registrationPage.endDay())
                                || registrationPage.today().isBefore(registrationPage.endDay())),
                        "Дата " + registrationPage.today() + " должна быть между " + registrationPage.startDay()
                                + " и " + registrationPage.endDay()
                );
            });
        });

        step("Нажать на дропдаун \"Статус документа\"", () -> {
            registrationPage.statusDocument();
            step("Появляется выпадающий список в виде чек-бокса со статусами " +
                    "\"Черновик\", \"Проведен\", \"Удален\"", () -> {
                checkDropDown();
            });
            step("Выбрать чек-бокс \"Удален\"", () -> {
                registrationPage.deletedCheckBox();
                step("В поле \"Статус документа\" - отображается \"Удален\"", () -> {
                    assertEquals(registrationPage.textDropdown(), Status.DELETED.get(), "Статус документа не выбран Удален");
                });
            });
        });

        step("Нажать на дропдаун \"Пользователи\"", () -> {
            step("Появляется выпадающий список с пользователями", () -> {
                registrationPage.userDropdown();
            });
            step("В поле поиска ввести имя", step -> {
                step.parameter("username", Constants.FULLNAME);
                step("Имя пользователя отображается в поле поиска", () -> {
                    registrationPage.setValueUser(Constants.FULLNAME);
                });
            });
            step("Выбрать пользователя - " + Constants.FULLNAME, () -> {
                step("В поле \"Пользователи\" - отображается" + Constants.FULLNAME + "", () -> {
                    registrationPage.selectUser(Constants.FULLNAME);
                });
            });
        });
        step("Нажать на кнопку \"Применить\"", () -> {
            registrationPage.buttonSummit();
            step("Проверить колонку \"Дата\"", () -> {
                step("В колонке \"Дата\" у документов отображаются только даты текущего месяца", () -> {
                    checkTableDate();
                });
            });
            step("Проверить колонку \"Статус\"", () -> {
                step("В колонке \"Статус\" у документов отображается только статус \"Удален\"", () -> {
                    checkTableStatus();
                });
            });
            step("Проверить колонку \"Пользователи\"", () -> {
                step("В колонке \"Пользователь\" у документов отображается только пользователь" + Constants.USERNAME + "", () -> {
                    checkTableName();
                });
            });
            step("Проверить \"Чипсы\" списка документов", () -> {
                step("В Чипсах списка документов отображаются фильтры:\n" +
                        "\n" +
                        "Дата текущего месяца\n" +
                        "\n" +
                        "Статус документа \"Удален\"\n" +
                        "\n" +
                        "Пользователь " + Constants.USERNAME + "", () -> {
                    checkChip(Constants.USERNAME, Status.DELETED.get());
                });
            });
        });
    }
    private void checkDropDown(){
        List<String> itemsList = $$x("//khan-dropdown-list-item//li").texts();
        assertEquals(itemsList.size(), 3);
        assertTrue(itemsList.contains(Status.POSTED.get()),"Проведен не виден");
        assertTrue(itemsList.contains(Status.DRAFT.get()),"Черновик не виден");
        assertTrue(itemsList.contains(Status.DELETED.get()),"Удален не виден");
    }
    private void checkTableDate() {
        Map<String, Integer> headerMap = helper.getHeaderIndexMap();
        Integer dateIndex = headerMap.get("Дата");
        helper.loaderDisappear();
        for (SelenideElement row : rows) {
            row.shouldBe(visible, Duration.ofSeconds(10));
            ElementsCollection rowElement = row.$$x(".//td");
            String dateStr = rowElement.get(dateIndex).getText().trim();
            String dateOnly = dateStr.split("\\|")[0].trim();

            LocalDate localDate = LocalDate.parse(dateOnly, Constants.FORMATTER);
            assertTrue(
                    (localDate.isEqual(Constants.START_DATE) || localDate.isAfter(Constants.START_DATE)) &&
                            (localDate.isEqual(Constants.END_DATE) || localDate.isBefore(Constants.END_DATE)),
                    "Дата " + localDate + " должна быть между " + Constants.START_DATE + " и " + Constants.END_DATE
            );
        }
    }

    private void checkTableStatus() {
        Map<String, Integer> headerMap = helper.getHeaderIndexMap();
        Integer statusIndex = headerMap.get("Статус документа");
        helper.loaderDisappear();
        for (SelenideElement row : rows) {
            row.shouldBe(visible, Duration.ofSeconds(10));
            ElementsCollection rowElement = row.$$x(".//td");

            String status = rowElement.get(statusIndex).getText().trim();
            assertTrue(
                    status.equals("Проведен") || status.equals("Удален"),
                    "Статус должен быть 'Проведен' или 'Удален', но был: " + status
            );
        }
    }

    private void checkTableName() {
        Map<String, Integer> headerMap = helper.getHeaderIndexMap();
        Integer nameIndex = headerMap.get("Пользователь");
        helper.loaderDisappear();
        for (SelenideElement row : rows) {
            row.shouldBe(visible, Duration.ofSeconds(10));
            ElementsCollection rowElement = row.$$x(".//td");

            String name = rowElement.get(nameIndex).getText().trim();
            assertEquals(Constants.USERNAME, name, "Имя должно быть 'Миша', но было: " + name);
        }
    }

    private void checkChip(String userCheck, String statusCheck) {
        String userFilter = $x("//khan-filter-chips//span[contains(text(), 'Пользователь:')]").getText().trim();
        String statusFilter = $x("//khan-filter-chips//span[contains(text(), 'Статус документа')]").getText().trim();
        String dateFilter = $x("//khan-filter-chips//span[contains(text(), 'по')]").getText().trim();

        String userFormated = userFilter.replace("Пользователь: ", "").split(" ")[0];
        String statusFormated = statusFilter.replace("Статус документа: ", "");
        String[] parts = dateFilter.replace("C ", "").split(" по ");
        String fromDateStr = parts[0].split(",")[0].trim();
        String toDateStr = parts[1].split(",")[0].trim();


        LocalDate localDate = LocalDate.now();
        LocalDate from = LocalDate.parse(fromDateStr, Constants.FORMATTER);
        LocalDate to = LocalDate.parse(toDateStr, Constants.FORMATTER);

        assertTrue(
                !localDate.isBefore(from) && !localDate.isAfter(to),
                "Дата " + localDate + " вне допустимого диапазона: " + from + " - " + to
        );
        assertEquals(userCheck, userFormated);
        assertEquals(statusCheck, statusFormated);
    }
}
