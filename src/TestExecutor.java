import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestExecutor {
    private WebDriver driver;
    private JsonReader jsonReader = new JsonReader();

    @DataProvider(name = "test-cases-provider")
    public Object[][] provideTestCases() throws IOException {
        List<TestCase> testCases = jsonReader.readTestCases("/Users/hrithikpuppala/IdeaProjects/NBCNews/src/nbcnews.json");
        Object[][] data = new Object[testCases.size()][1];
        for (int i = 0; i < testCases.size(); i++) {
            data[i][0] = testCases.get(i);
        }
        return data;
    }

    @Test(dataProvider = "test-cases-provider")
    public void runTest(TestCase testCase) {
        driver = WebDriverFactory.getDriver("chrome");
        driver.get("https://www.nbcnews.com/");
        WebElement element;
        switch (testCase.getFindElementBy()) {
            case "cssSelector":
                element = driver.findElement(By.cssSelector(testCase.getValue()));
                break;
            case "linkText":
                element = driver.findElement(By.linkText(testCase.getValue()));
                break;
            default:
                throw new IllegalArgumentException("Not found findElementBy");
        }
        element.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlToBe(testCase.getExpectedData()));
        String actualData = driver.getCurrentUrl();
        AssertionUtility.performAssertion(testCase.getAssertionType(), testCase.getExpectedData(), actualData);
    }
    @Test
    public void testDynamicContentLoad() {
        driver = WebDriverFactory.getDriver("chrome");
        driver.get("https://www.nbcnews.com/");

        List<WebElement> initialArticles = driver.findElements(By.cssSelector("div.headline___38lAR"));
        ArrayList<String> initialTitles = new ArrayList<>();
        for (WebElement article : initialArticles) {
            initialTitles.add(article.getText());
        }

        driver.navigate().refresh();
        List<WebElement> newArticles = driver.findElements(By.cssSelector("div.headline___38lAR"));
        ArrayList<String> newTitles = new ArrayList<>();
        for (WebElement article : newArticles) {
            newTitles.add(article.getText());
        }

        Assert.assertTrue(Collections.disjoint(initialTitles, newTitles), "No new articles loaded after refresh.");
    }

}
