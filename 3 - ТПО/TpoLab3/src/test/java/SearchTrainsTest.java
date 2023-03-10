import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class SearchTrainsTest {
    private WebDriver driver;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchTrains() {
        driver.get("https://www.tutu.ru/");
        driver.manage().window().setSize(new Dimension(1163, 1032));
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[4]/div/div[1]/div[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[5]/div/div[2]/div[1]/div/form/div/div/div[1]/div/div[1]/div[1]/input")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[5]/div/div[2]/div[1]/div/form/div/div/div[1]/div/div[1]/div[1]/input")).sendKeys("Астрахань");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[5]/div/div[2]/div[1]/div/form/div/div/div[3]/div/div[1]/div[1]/input")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[5]/div/div[2]/div[1]/div/form/div/div/div[3]/div/div[1]/div[1]/input")).sendKeys("Казань");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[5]/div/div[2]/div[1]/div/form/div/div/div[4]/div/div[1]/div/input")).click();
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[5]/div/div[2]/div[1]/div/form/div/div/div[4]/div/div[1]/div/input")).sendKeys("25.05.2022");

        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div[1]/div[5]/div/div[2]/div[1]/div/form/div/div/div[6]/button/span/span[3]")).click();
        {
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div/div/div[1]/div[1]/span"));
            assert (elements.size() > 0);
        }
    }
}
