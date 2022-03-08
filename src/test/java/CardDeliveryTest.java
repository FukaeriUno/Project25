import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    String city;
    Date date;
    Date date2;
    String name;
    String tel;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public Date DatePlusThree(Date date) {


        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.DATE, 3);

        Date currentDatePlusThree = c.getTime();
        return currentDatePlusThree;
    }

    public String convertStringToDate(Date indate) {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd.MMM.yyyy");

        dateString = sdfr.format(indate);

        return dateString;
    }

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker(new Locale("ru"));
        this.name = faker.name().fullName();
        this.city = faker.address().city();
        this.tel = faker.phoneNumber().phoneNumber();
        this.date = DatePlusThree(new Date());
        this.date2 = DatePlusThree(date);
    }

    @Test
    public void shouldRescheduleNote() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(sdf.format(date));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(tel);
        $(By.className("checkbox__box")).click();
        $(".button__text").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(sdf.format(date2));
        $(".button__text").click();
        $("[data-test-id=replan-notification] button").click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
