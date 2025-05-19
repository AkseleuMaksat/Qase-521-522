package kz.Akseleu;

import kz.Akseleu.constants.Constants;
import kz.Akseleu.enums.Status;
import kz.Akseleu.helper.TableHelper;
import kz.Akseleu.pages.AuthorizationPage;
import kz.Akseleu.pages.DecommissionPage;
import kz.Akseleu.pages.ProductPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

public class DecommissionFilterUITest extends BaseTest {
    private final TableHelper tableHelper = new TableHelper();
    private final DecommissionPage decommissionPage = new DecommissionPage();
    private final ProductPage productPage = new ProductPage();

    @BeforeEach
    public void login() {
        AuthorizationPage auth = AuthorizationPage.open();
        auth.authorization(Constants.LOGIN, Constants.PASSWORD);
    }

    @Test
    public void woper522() {
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
                assertEquals(3, decommissionPage.statusDropDownList().size());
                assertTrue(decommissionPage.statusDropDownList().contains(Status.PROVIDED.get()), "Проведен не виден");
                assertTrue(decommissionPage.statusDropDownList().contains(Status.DRAFT.get()), "Черновик не виден");
                assertTrue(decommissionPage.statusDropDownList().contains(Status.DELETED.get()), "Удален не виден");
            });
            step("Выбрать чек-бокс \"Удален\"", () -> {
                decommissionPage.selectFromDropdown(Status.DELETED);
                step("В поле \"Статус документа\" - отображается \"Удален\"", () ->
                        assertEquals(decommissionPage.statusTextDropdown(), Status.DELETED.get(),
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
                            () -> checkTableStatus(Status.DELETED)
                    )
            );
            step("Проверить колонку \"Пользователи\"", () ->
                    step("В колонке \"Пользователь\" у документов отображается только пользователь" + Constants.USERNAME,
                            this::checkTableName));
            step("Проверить \"Чипсы\" списка документов", () ->
                    step("В Чипсах списка документов отображаются фильтры: Дата текущего месяца " +
                            "Статус документа \"Удален\" Пользователь " + Constants.FULL_NAME, () -> {
                        checkChipDate(Constants.START_CURRENT_MONTH, Constants.END_CURRENT_MONTH);
                        checkChipStatus();
                        checkChipUser(Constants.FULL_NAME);
            }));
        });
    }
    @Test
    public void woper521(){
        step("Открыта страница \"Списание\"", decommissionPage::open);
        step("Нажать на кнопку \"Фильтр\"", () -> {
            decommissionPage.filterButton();
            step("Открывается модальное окно фильтра", decommissionPage::filterPanelExist);
        });
        step("Выбрать таб \"Неделя\"", () -> {
            decommissionPage.weekButton();
            step("В поле \"Дэйтскролл\" -  отображаются даты текущей недели", () ->
                    assertTrue(
                            (decommissionPage.today().isEqual(decommissionPage.startDay())
                                    || decommissionPage.today().isAfter(decommissionPage.startDay()))
                                    && (decommissionPage.today().isEqual(decommissionPage.endDay())
                                    || decommissionPage.today().isBefore(decommissionPage.endDay())),
                            "Дата " + decommissionPage.today() + " должна быть между " + decommissionPage.startDay()
                                    + " и " + decommissionPage.endDay()
                    ));
        });
        step("В поле \"Статус документа\" выбраны статус Проведен и черновик", () -> {
            decommissionPage.statusDocument();
            decommissionPage.selectFromDropdown(Status.PROVIDED);
            decommissionPage.selectFromDropdown(Status.DRAFT);
            decommissionPage.statusDocument();
        });

        step("Ввести в поле \"Поиск по штрихкоду и названию\" название товара "+Constants.BARCODE, () -> {
            decommissionPage.clickBarcodeInput();
            decommissionPage.setBarcodeInput(Constants.BARCODE);
            step("Название товара отображается в поле \"Поиск по штрихкоду и названию\"", () ->{
                assertEquals(decommissionPage.barCodeText(), Constants.BARCODE);
            } );
        });
        step("Нажать на кнопку \"Применить\"", () -> {
            decommissionPage.clickSummit();
            step("Проверить колонку \"Дата\"", () ->
                    step("В колонке \"Дата\" отображаются документы только с датами текущей недели",
                            this::checkTableDate));
            step("Проверить колонку \"Статус\"", () ->
                    step("В колонке \"Статус\" у документов отображаются статусы \"Проведен\" и \"Черновик\"",()->
                            checkTableStatus(Status.DRAFT, Status.PROVIDED)));
            step("Проверить \"Чипсы\" списка документов", () ->
                    step("В Чипсах списка документов отображаются фильтры: Дата за нынешнюю неделю" +
                            " Статус документа: \""+Status.DRAFT.get()+"\", \""+Status.PROVIDED.get()+"\"" +
                            " Штрихкод и Название: "+Constants.BARCODE, () -> {
                        checkChipDate(Constants.START_CURRENT_WEEK, Constants.END_CURRENT_WEEK);
                        checkChipStatus();
                        checkChipBarCode(Constants.BARCODE);
                    }));
        });

        step("Нажать на номер первого документа в списке", () -> {
            step("Открывается документ списания", () -> decommissionPage.openFirstDocument());
            step("В поле \"Поиск по таблице\" ввести название товара", ()->{
                productPage.setSearchText();
                tableHelper.loaderDisappear();
                step("Поле заполнено",sub->{
                    sub.parameter("Поле заполнено", Constants.BARCODE);
                    assertEquals(Constants.BARCODE,productPage.checkSearchLine(),"Поле не заполнено");
                });
                step("В таблице товаров будет отображаться товар с данным названием", this::checkTableBarCode);
            });
            step("Нажать кнопку \"Закрыть\"",()->{
               productPage.clickCloseButton();
               tableHelper.loaderDisappear();
               step("Появляется диалоговое окно \"Подтверждение действия\"",()->{
                   productPage.checkDynamicLogger();
               });
               step("Нажать кнопку \"Да\"", ()->{
                   productPage.clickYesButtonForLogger(productPage.checkDynamicLogger());
                   step("Документ закрывается Открывается страница с списком документов", ()->{
                       productPage.inputShouldNotExist();
                       decommissionPage.shouldBeTable();
                   });
               });
            });
        });
        step("Повторить шаги 5.1, 5.2, 5.3", () -> {
            checkTableDate();
            checkTableStatus(Status.DRAFT, Status.PROVIDED);
            checkChipDate(Constants.START_CURRENT_WEEK, Constants.END_CURRENT_WEEK);
            checkChipStatus();
            checkChipBarCode(Constants.BARCODE);
        });
        step("Нажать на номер второго документа в списке", () -> {
            step("Открывается документ списания", () -> decommissionPage.openSecondProduct());
            step("В поле \"Поиск по таблице\" ввести название товара", ()->{
                productPage.setSearchText();
                tableHelper.loaderDisappear();
                step("Поле заполнено",()->{
                    assertEquals(Constants.BARCODE, productPage.checkSearchLine(),"Поле не заполнено");
                });
                step("В таблице товаров будет отображаться товар с данным названием", this::checkTableBarCode);
            });
            step("Нажать кнопку \"Закрыть\"",()->{
                productPage.clickCloseButton();
                tableHelper.loaderDisappear();
                step("Появляется диалоговое окно \"Подтверждение действия\"",()->{
                    productPage.checkDynamicLogger();
                });
                step("Нажать кнопку \"Да\"", ()->{
                    productPage.clickYesButtonForLogger(productPage.checkDynamicLogger());
                    step("Документ закрывается Открывается страница с списком документов", ()->{
                        productPage.inputShouldNotExist();
                        decommissionPage.shouldBeTable();
                    });
                });
            });
        });
        step("Нажать на номер последнего документа в списке",()->{
            step("Открывается документ списания", () -> decommissionPage.openLastProduct());
            step("В поле \"Поиск по таблице\" ввести название товара", ()->{
                productPage.setSearchText();
                tableHelper.loaderDisappear();
                step("Поле заполнено",()->{
                    assertEquals(Constants.BARCODE,productPage.checkSearchLine(),"Поле не заполнено");
                });
                step("В таблице товаров будет отображаться товар с данным названием", this::checkTableBarCode);
            });
            step("Нажать кнопку \"Закрыть\"",()->{
                productPage.clickCloseButton();
                tableHelper.loaderDisappear();
                step("Появляется диалоговое окно \"Подтверждение действия\"",()->{
                    productPage.checkDynamicLogger();
                });
                step("Нажать кнопку \"Да\"", ()->{
                    productPage.clickYesButtonForLogger(productPage.checkDynamicLogger());
                    step("Документ закрывается Открывается страница с списком документов", ()->{
                        productPage.inputShouldNotExist();
                        decommissionPage.shouldBeTable();
                    });
                });
            });
        });
        step("Повторить шаги 6, 7 и 8 для документов со второй и последней страницы",()->{
            //6
            decommissionPage.openFirstDocument();
            productPage.setSearchText();
            tableHelper.loaderDisappear();
            assertEquals(Constants.BARCODE,productPage.checkSearchLine(),"Поле не заполнено");
            checkTableBarCode();
            productPage.clickCloseButton();
            tableHelper.loaderDisappear();
            productPage.checkDynamicLogger();
            productPage.clickYesButtonForLogger(productPage.checkDynamicLogger());
            productPage.inputShouldNotExist();
            decommissionPage.shouldBeTable();
            //7
            checkTableDate();
            checkTableStatus(Status.DRAFT, Status.PROVIDED);
            checkChipDate(Constants.START_CURRENT_WEEK, Constants.END_CURRENT_WEEK);
            checkChipStatus();
            checkChipBarCode(Constants.BARCODE);
            //8
            decommissionPage.openSecondProduct();
            productPage.setSearchText();
            tableHelper.loaderDisappear();
            assertEquals(Constants.BARCODE, productPage.checkSearchLine(),"Поле не заполнено");
            productPage.clickCloseButton();
            tableHelper.loaderDisappear();
            productPage.checkDynamicLogger();
            productPage.clickYesButtonForLogger(productPage.checkDynamicLogger());
            productPage.inputShouldNotExist();
            decommissionPage.shouldBeTable();
        });
    }
    private void checkTableBarCode(){
        for (String list : tableHelper.getColumnData(productPage.getTableRow(),"Название товара")){
            assertEquals(Constants.BARCODE, list.trim(),"Штрих код не найден");
        }
    }

    private void checkChipBarCode(String username) {
        assertEquals(username, decommissionPage.barCodeFormated(),
                "В chip неправильный username");
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

    private void checkTableStatus(Status... expectedValues) {
        List<String> statuses = new ArrayList<>();
        Arrays.asList(expectedValues).forEach(status -> statuses.add(status.get()));
        for (String actualValue : tableHelper.getColumnData(decommissionPage.getTableRows(), "Статус документа")) {
            assertTrue(
                    statuses.contains(actualValue),
                    "Статус должно быть одним из '"+ statuses +"', но был: " + actualValue
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

    private void checkChipStatus() {
        String needCheckText = decommissionPage.statusFormated().trim();
        if(needCheckText.contains(",")){
            String[] parts = needCheckText.split(",");
            for (String status : parts) {
                checkStatusAgainstEnum(status);
            }
        }else {
            checkStatusAgainstEnum(needCheckText);
        }
    }
    private void checkStatusAgainstEnum(String status) {
        boolean statusAgainstEnum = false;
        String statusValue ="";
        for (Status statu : Status.values()) {
            if(statu.get().equals(status)){
                statusValue= statu.get();
                statusAgainstEnum = true;
                break;

            }
        }
        if(statusAgainstEnum){
            assertEquals(statusValue, status, "В Chip неправильный status");
        }
    }

    private void checkChipUser(String username) {
        assertEquals(username, decommissionPage.userNameFormated(),
                "В chip неправильный username");
    }
}
