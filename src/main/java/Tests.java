import com.bigmans.testslibrary.additions.InitializationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

/**
 * Класс для тестов библиотеки Военмеха
 * @author mismailov
 */
public class Tests {

    /** Локатор кнопки - Войти */
    private final By clicksInputPath = By.xpath("//div[@class='moduletable auth']//input[@class='button']");
    /** Локатор вкладки - РАсширенный поиск */
    private final By uncoverPath = By.xpath("//span[@class = 'ui-accordion-header-icon ui-icon ui-icon-triangle-1-e'][1]");
    /** Локатор поля ввода автора книги */
    private final By inputAuthorPath = By.xpath("//input[@id ='author']");
    /** Локатор кнопки - Поиск */
    private final By searchPath = By.xpath("//input[@id ='search_button']");
    /** Локатор ответа после поиска книги */
    private final By checkLoadingBooks = By.xpath("//td[@id = 'req_description_cell']");

    /**
     * Тест инициализации пользователя
     */
    @Test
    public  void InitializationTest() {
        WebDriver driver = new ChromeDriver();
        InitializationData initialization = new InitializationData();
        helpFunc(driver, initialization.getUrl1(), initialization);
        String userName = driver.findElement((By.className("login-greeting"))).getText();
        Assertions.assertEquals(initialization.getCheckTextTest1(), userName);
    }

    /**
     * Тест поиска книги
     */
    @Test
    public void searchBookTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        InitializationData initialization = new InitializationData();
        helpFunc(driver, initialization.getUrl2(), initialization);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try {
            driver.findElement(uncoverPath).click();
            driver.findElement(inputAuthorPath).sendKeys(initialization.getSearchBook());
            driver.findElement(searchPath).click();
        } catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println(ex.getRawMessage());
        }
        Thread.sleep(6000L);
        Assertions.assertEquals(initialization.getCheckTextTest2(), driver.findElement(checkLoadingBooks).getText());
    }

    /**
     * Вспомогательная функция для входа в свой профиль
     * @param driver -  интерфейс удаленного управления
     * @param url - ссылка, с которой мы начинаем все наши действия
     * @param initialization - вспомогательный класс
     */
    private void helpFunc( WebDriver driver, String url, InitializationData initialization){
        driver.get(url);
        driver.findElement(By.className("inputbox")).sendKeys(initialization.getLastName());
        driver.findElement(By.id("modlgn-passwd")).sendKeys(initialization.getPassword());
        driver.findElement(clicksInputPath).click();
    }
}
