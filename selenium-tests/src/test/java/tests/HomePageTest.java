package tests;

import base.BaseTest;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ConfigReader;
import utils.RandomDataGenerator;

public class HomePageTest extends BaseTest {

    private HomePage openHomePage() {
        HomePage homePage = new HomePage(driver);
        homePage.open(ConfigReader.get("base.url"));
        return homePage;
    }

    @Test(description = "Home page title contains 'Moobius'")
    public void homePageTitleContainsMoobius() {
        HomePage homePage = openHomePage();

        Assert.assertTrue(homePage.getPageTitle().contains("Moobius"),
                "Home page title should contain 'Moobius', but was: " + homePage.getPageTitle());
    }

    @Test(description = "Moobius logo is visible on the home page")
    public void moobiusLogoIsVisibleOnHomePage() {
        HomePage homePage = openHomePage();

        Assert.assertTrue(homePage.isLogoDisplayed(),
                "Moobius logo should be visible on the home page");
    }

    @Test(description = "A cookie can be added, read back, and then deleted")
    public void cookieCanBeAddedReadAndDeleted() {
        openHomePage();

        Cookie testCookie = new Cookie("selenium_test", "hello123");
        driver.manage().addCookie(testCookie);

        Cookie retrieved = driver.manage().getCookieNamed("selenium_test");
        Assert.assertNotNull(retrieved, "Cookie should exist after being added");
        Assert.assertEquals(retrieved.getValue(), "hello123",
                "Retrieved cookie value should match what was set");

        driver.manage().deleteCookieNamed("selenium_test");
        Assert.assertNull(driver.manage().getCookieNamed("selenium_test"),
                "Cookie should no longer exist after deletion");
    }

    @Test(description = "Searching with a random term navigates to a search results page")
    public void searchingWithRandomTermNavigatesToResultsPage() {
        HomePage homePage = openHomePage();
        String term = RandomDataGenerator.randomSearchTerm();

        homePage.searchFor(term);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("talalati-lista"),
                "URL should reflect a search results page after searching, but was: " + currentUrl
        );
    }

    @Test(description = "Page can be scrolled to the bottom using JavaScript")
    public void pageCanBeScrolledToBottomWithJavaScript() {
        HomePage homePage = openHomePage();

        homePage.scrollToBottom();

        Assert.assertTrue(homePage.getVerticalScrollPosition() > 0,
                "Scroll position should be greater than 0 after scrolling to bottom");
    }

    @Test(description = "Newsletter form can be filled, checkbox checked, and form submitted")
    public void newsletterFormCanBeFilledAndSubmitted() {
        HomePage homePage = openHomePage();
        String name  = RandomDataGenerator.randomFirstName();
        String email = RandomDataGenerator.randomEmail();

        homePage.fillAndSubmitNewsletterForm(name, email);

        Assert.assertTrue(homePage.isNewsletterNormalCheckboxChecked(),
                "Newsletter checkbox should be checked after clicking it");
        Assert.assertTrue(driver.getCurrentUrl().contains("moobius.hu"),
                "Should remain on moobius.hu after form submission");
    }
}
