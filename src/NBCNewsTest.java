import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NBCNewsTest {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void openNBCNewsHomePage() {
        driver.get("https://www.nbcnews.com/");
    }

    @Test
    public void testWatchLiveButton() {
        driver.get("https://www.nbcnews.com/");
        WebElement watchLiveButton = driver.findElement(By.linkText("Watch live"));
        watchLiveButton.click();

        String expectedLiveUrl = "https://www.nbcnews.com/now";
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(actualUrl, expectedLiveUrl, "The live URL is not as expected.");
    }

    @Test
    public void testMainNavigationLinks() {
        driver.get("https://www.nbcnews.com");

        String expectedElectionUrl = "https://www.nbcnews.com/politics/2024-election/live-blog/election-2024-live-updates-rcna134579";
        String expectedPoliticsUrl = "https://www.nbcnews.com/politics";
        String expectedWorldUrl = "https://www.nbcnews.com/world";
        String expectedBusinessUrl = "https://www.nbcnews.com/business";
        String expectedUSNewsUrl = "https://www.nbcnews.com/us-news";


        WebElement politicsLink = driver.findElement(By.cssSelector("a[href='https://www.nbcnews.com/politics']"));
        politicsLink.click();
        String actualPoliticsUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualPoliticsUrl, expectedPoliticsUrl, "Politics URL does not match expected.");

        driver.navigate().back();

        WebElement electionLink = driver.findElement(By.cssSelector("a[href*='2024-election']"));
        electionLink.click();
        String actualElectionUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualElectionUrl, expectedElectionUrl, "Election URL does not match expected.");

        driver.navigate().back();


        WebElement worldLink = driver.findElement(By.cssSelector("a[href='https://www.nbcnews.com/world']"));
        worldLink.click();
        String actualWorldUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualWorldUrl, expectedWorldUrl, "World URL does not match expected.");

        driver.navigate().back();

        WebElement businessLink = driver.findElement(By.cssSelector("a[href='https://www.nbcnews.com/business']"));
        businessLink.click();
        String actualBusinessUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualBusinessUrl, expectedBusinessUrl, "Business URL does not match expected.");

        driver.navigate().back();

        WebElement USNewsLink = driver.findElement(By.cssSelector("a[href='https://www.nbcnews.com/us-news']"));
        USNewsLink.click();
        String actualUSNewsUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUSNewsUrl, expectedUSNewsUrl, "Politics URL does not match expected.");

        driver.navigate().back();
    }

    @Test
    public void testDynamicContentLoad() {
        driver.get("https://www.nbcnews.com/");

        // Get the current articles list
        List<WebElement> initialArticles = driver.findElements(By.cssSelector("div.headline___38lAR"));
        ArrayList<String> initialTitles = new ArrayList<>();
        for (WebElement article : initialArticles) {
            initialTitles.add(article.getText());
        }

        // Refresh the page and get the new list of articles
        driver.navigate().refresh();
        List<WebElement> newArticles = driver.findElements(By.cssSelector("div.headline___38lAR"));
        ArrayList<String> newTitles = new ArrayList<>();
        for (WebElement article : newArticles) {
            newTitles.add(article.getText());
        }

        // Check if at least one new article is loaded
        Assert.assertTrue(Collections.disjoint(initialTitles, newTitles), "No new articles loaded after refresh.");
    }

    @Test
    public void testResponsiveDesign() {
        driver.get("https://www.nbcnews.com/");

        Dimension desktop = new Dimension(1024, 768);
        Dimension tablet = new Dimension(768, 1024);
        Dimension mobile = new Dimension(375, 667);

        //Desktop
        driver.manage().window().setSize(desktop);
        // Tests

        // Tablet
        driver.manage().window().setSize(tablet);
        // Tests

        // Mobile
        driver.manage().window().setSize(mobile);
        // Tests

        // Reset
        driver.manage().window().maximize();
    }


    @Test
    public void testFooterLinksValidation() {
        driver.get("https://www.nbcnews.com/");

        // Scroll to bottom
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        String expectedAboutUrl = "https://www.nbcnews.com/information/nbc-news-info/about-nbc-news-digital-n1232178";

        WebElement aboutLink = driver.findElement(By.cssSelector("a[href*='/about-nbc-news-digital']"));
        aboutLink.click();
        String actualAboutUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualAboutUrl, expectedAboutUrl, "About URL does not match expected.");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
