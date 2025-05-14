package kz.Akseleu;

import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$x;

public class ChipsCheck {
    String userFilter = $x("//khan-filter-chips//span[contains(text(), 'Пользователь:')]").getText().trim();
    String statusFilter = $x("//khan-filter-chips//span[contains(text(), 'Статус документа')]").getText().trim();
    String dateFilter = $x("//khan-filter-chips//span[contains(text(), 'по')]").getText().trim();
    public void checkChip(String userCheck, String statusCheck) {
        String userFormated = userFilter.replace("Пользователь: ", "").split(" ")[0];
        String statusFormated = statusFilter.replace("Статус документа: ", "");
        String[] parts = dateFilter.replace("C ", "").split(" по ");
        String fromDateStr = parts[0].split(",")[0].trim();
        String toDateStr = parts[1].split(",")[0].trim();


        LocalDate localDate = LocalDate.now();
        LocalDate from = LocalDate.parse(fromDateStr, UmagTest.FORMATTER);
        LocalDate to = LocalDate.parse(toDateStr, UmagTest.FORMATTER);

        Assertions.assertTrue(
                !localDate.isBefore(from) && !localDate.isAfter(to),
                "Дата " + localDate + " вне допустимого диапазона: " + from + " - " + to
        );
        Assertions.assertEquals(userCheck, userFormated );
        Assertions.assertEquals(statusCheck, statusFormated);
    }
}
