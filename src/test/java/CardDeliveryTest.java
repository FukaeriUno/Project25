import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    String city;
    String date;
    String date2;
    String name;
    String tel;

    String planningDate = generateDate(4);

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker(new Locale("ru"));
        this.name = faker.name().fullName();
        this.city = faker.address().city();
        this.tel = faker.phoneNumber().phoneNumber();
        this.date = generateDate(4);
        this.date2 = generateDate(8);
    }

    @Test
    public void shouldRescheduleNote() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(tel);
        $(By.className("checkbox__box")).click();
        $(".button__text").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date2);
        $(".button__text").click();
        $("[data-test-id=replan-notification] button").click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + date2), Duration.ofSeconds(15));
    }
}
