package kz.Akseleu;

import kz.Akseleu.constants.Constants;
import kz.Akseleu.enums.Status;
import kz.Akseleu.helper.TableHelper;
import kz.Akseleu.pages.AuthorizationPage;
import kz.Akseleu.pages.DecommissionPage;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

public class DecommissionFilterUITest extends BaseTest {
    private final TableHelper tableHelper = new TableHelper();
    private final DecommissionPage decommissionPage = new DecommissionPage();

    @Test
    public void woper522() {
        AuthorizationPage auth = AuthorizationPage.open();
        auth.authorization(Constants.LOGIN, Constants.PASSWORD);
        step("Открыта страница \"Списание\"", decommissionPage::open);
        step("Нажать на кнопку \"Фильтр\"", () -> {
            decommissionPage.filterButton();
            step("Открывается модальное окно фильтра", decommissionPage::filterPanelExist);
        });
        step("Нажать на таб \"Месяц\"", () -> {
            decommissionPage.monthButton();
            step("В поле \"Дэйтскролл\" - отображаются даты текущего месяца", () ->
                    assertTrue(
                            (decommissionPage.today().isEqual(decommissionPage.startDay())
                                    || decommissionPage.today().isAfter(decommissionPage.startDay()))
                                    && (decommissionPage.today().isEqual(decommissionPage.endDay())
                                    || decommissionPage.today().isBefore(decommissionPage.endDay())),
                            "Дата " + decommissionPage.today() + " должна быть между " + decommissionPage.startDay()
                                    + " и " + decommissionPage.endDay()
                    ));
        });

        step("Нажать на дропдаун \"Статус документа\"", () -> {
            decommissionPage.statusDocument();
            step("Появляется выпадающий список в виде чек-бокса со статусами " +
                    "\"Черновик\", \"Проведен\", \"Удален\"", () -> {
                assertEquals(3, decommissionPage.dropDownList().size());
                assertTrue(decommissionPage.dropDownList().contains(Status.PROVIDED.get()), "Проведен не виден");
                assertTrue(decommissionPage.dropDownList().contains(Status.DRAFT.get()), "Черновик не виден");
                assertTrue(decommissionPage.dropDownList().contains(Status.DELETED.get()), "Удален не виден");
            });
            step("Выбрать чек-бокс \"Удален\"", () -> {
                decommissionPage.selectFromDropdown(Status.DELETED);
                step("В поле \"Статус документа\" - отображается \"Удален\"", () ->
                        assertEquals(decommissionPage.textDropdown(), Status.DELETED.get(),
                                "Статус документа не выбран Удален"));
            });
        });

        step("Нажать на дропдаун \"Пользователи\"", () -> {
            step("Появляется выпадающий список с пользователями", decommissionPage::openUserDropdown);
            step("В поле поиска ввести имя", step -> {
                step.parameter("username", Constants.FULL_NAME);
                step("Имя пользователя отображается в поле поиска", () ->
                        decommissionPage.setValueUser(Constants.FULL_NAME));
            });
            step("Выбрать пользователя - " + Constants.FULL_NAME, () ->
                    step("В поле \"Пользователи\" - отображается" + Constants.FULL_NAME,
                            decommissionPage::selectFirstUser));
        });
        step("Нажать на кнопку \"Применить\"", () -> {
            decommissionPage.clickSummit();
            step("Проверить колонку \"Дата\"", () ->
                    step("В колонке \"Дата\" у документов отображаются только даты текущего месяца",
                            this::checkTableDate));
            step("Проверить колонку \"Статус\"", () ->
                    step("В колонке \"Статус\" у документов отображается только статус \"Удален\"",
                            this::checkTableStatus));
            step("Проверить колонку \"Пользователи\"", () ->
                    step("В колонке \"Пользователь\" у документов отображается только пользователь" + Constants.USERNAME,
                            this::checkTableName));
            step("Проверить \"Чипсы\" списка документов", () ->
                    step("В Чипсах списка документов отображаются фильтры: Дата текущего месяца " +
                            "Статус документа \"Удален\" Пользователь " + Constants.FULL_NAME, () -> {
                        checkChipDate(Constants.START_CURRENT_MONTH, Constants.END_CURRENT_MONTH);
                        checkChipStatus(Status.DELETED);
                        checkChipUser(Constants.FULL_NAME);
            }));
        });
    }

    private void checkTableDate() {
        for (String listOfDate : tableHelper.getColumnData(decommissionPage.getTableRows(), "Дата")) {
            String dateOnly = listOfDate.split("\\|")[0].trim();
            LocalDate localDate = LocalDate.parse(dateOnly, Constants.FORMATTER);
            assertTrue(
                    (localDate.isEqual(Constants.START_CURRENT_MONTH) || localDate.isAfter(Constants.START_CURRENT_MONTH)) &&
                            (localDate.isEqual(Constants.END_CURRENT_MONTH) || localDate.isBefore(Constants.END_CURRENT_MONTH)),
                    "Дата " + localDate + " должна быть между " + Constants.START_CURRENT_MONTH + " и " + Constants.END_CURRENT_MONTH
            );
        }
    }

    private void checkTableStatus() {
        for (String status : tableHelper.getColumnData(decommissionPage.getTableRows(), "Статус документа")) {
            assertTrue(
                    status.equals("Проведен") || status.equals("Удален"),
                    "Статус должен быть 'Проведен' или 'Удален', но был: " + status
            );
        }
    }

    private void checkTableName() {
        List<String> listOfName = tableHelper.getColumnData(decommissionPage.getTableRows(), "Пользователь");
        for (String name : listOfName) {
            assertEquals(Constants.USERNAME, name, "Имя должно быть 'Миша', но было: " + name);
        }
    }

    private void checkChipDate(LocalDate from, LocalDate to) {
        String[] parts = decommissionPage.dateFormated().replace("C ", "").split(" по ");
        String fromDateStr = parts[0].split(",")[0].trim();
        String toDateStr = parts[1].split(",")[0].trim();
        LocalDate fromDate = LocalDate.parse(fromDateStr, Constants.FORMATTER);
        LocalDate toDate = LocalDate.parse(toDateStr, Constants.FORMATTER);
        assertAll(
                () -> assertEquals(fromDate, from, "Начальный день не правильный"),
                () -> assertEquals(toDate, to, "Конечный день не правильный")
        );
    }

    private void checkChipStatus(Status status) {
        assertEquals(status.get(), decommissionPage.statusFormated(),
                "В chip неправильный status");

    }

    private void checkChipUser(String username) {
        assertEquals(username, decommissionPage.userNameFormated(),
                "В chip неправильный username");
    }
}
