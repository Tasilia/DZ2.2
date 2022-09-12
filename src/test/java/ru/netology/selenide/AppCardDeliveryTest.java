package ru.netology.selenide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {
    @BeforeEach
    void openURL() {
        open("http://localhost:9999");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void testSuccessMessage() {
        $x("//*[@placeholder='Город']").setValue("Москва");
        String planningDate = generateDate(3);
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.COMMAND, "A"),
                Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//*[@name='name']").setValue("Юрий Гагарин");
        $x("//*[@name='phone']").setValue("+77777777777");
        $x("//span[@class='checkbox__box']").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void testCalendarAndCity() {
        $x("//*[@placeholder='Город']").setValue("Ка");
        $x("//span[text()='Казань']").click();
        String planningDate = generateDate(7);
        String day = planningDate.substring(0, 2);
        $(".icon_name_calendar").click();
        if ($x("//td[@data-day][text()=" + day + "]").isDisplayed()) {
            $x("//td[@data-day][text()=" + day + "]").click();
        } else {
            $x("//div[@data-step=\"1\"]").click();
            $x("//td[@data-day][text()=" + day + "]").click();
        }
        $x("//*[@name='name']").setValue("Юрий Гагарин");
        $x("//*[@name='phone']").setValue("+77777777777");
        $x("//span[@class='checkbox__box']").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(visible);
    }
}
