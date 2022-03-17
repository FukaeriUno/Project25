import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    String planningDate = DataGenerator.generateDate(4);
    String planningDate2 = DataGenerator.generateDate(8);
    String name;
    String city;
    String tel;

    @BeforeEach
    void setUp() {
        name = DataGenerator.generateName();
        city = DataGenerator.generateCity();
        tel = DataGenerator.generatePhone();
    }

    @Test
    public void shouldRescheduleNote() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(tel);
        $(By.className("checkbox__box")).click();
        $(".button__text").click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), Duration.ofSeconds(15));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate2);
        $(".button__text").click();
        $("[data-test-id=replan-notification] button").click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate2), Duration.ofSeconds(15));
    }
}
