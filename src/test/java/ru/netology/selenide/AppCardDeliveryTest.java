package ru.netology.selenide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {
    @BeforeEach
    void openURL(){
        open("http://localhost:9999");
    }

    @Test
    void testSuccessMessage() {
        $x("//*[@placeholder='Город']").setValue("Москва");
        LocalDate dateAfterThreeDays = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        $x("//*[@placeholder='Дата встречи']").setValue("dateAfterThreeDays.format(formatter)");
        $x("//*[@name='name']").setValue("Юрий Гагарин");
        $x("//*[@name='phone']").setValue("+77777777777");
        $x("//span[@class='checkbox__box']").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $x("//div[contains(text(), \"Успешно!\")]").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void testCalendar() {
        LocalDate dateAfterSevenDays = LocalDate.now().plusDays(7);
        int day = dateAfterSevenDays.getDayOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String data = dateAfterSevenDays.format(formatter);
        $(".icon_name_calendar").click();
        if ($x("//td[@data-day][text()=" + day + "]").isDisplayed()) {
            $x("//td[@data-day][text()=" + day + "]").click();
        } else {
            $x("//div[@data-step=\"1\"]").click();
            $x("//td[@data-day][text()=" + day + "]").click();
        }
        $x("//input[@value='" + data + "']").shouldBe(visible);
    }

    @Test
    void testCity() {
        $x("//*[@placeholder='Город']").setValue("Ка");
        $x("//span[text()='Казань']").click();
        $x("//*[@placeholder='Город'][@value='Казань']").shouldBe(visible);
    }
}
