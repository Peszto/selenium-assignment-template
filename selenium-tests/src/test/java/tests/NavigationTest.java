package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ConfigReader;

import java.util.List;

public class NavigationTest extends BaseTest {

    private static final String GIFTS_URL = "https://moobius.hu/ajandektargyak";
    private static final String ABOUT_URL = "https://moobius.hu/rolunk";
    private static final String CATALOG_URL = "https://moobius.hu/katalogus-nyito";
    private static final String EBOOK_URL = "https://moobius.hu/ekonyveim";
    private static final String BOARD_GAME_URL = "https://moobius.hu/hir/gyonyoru-tematikus-tarsasjatekok";

    private HomePage openHomePage() {
        HomePage homePage = new HomePage(driver);
        homePage.open(ConfigReader.get("base.url"));
        return homePage;
    }

    @Test(description = "Key pages are reachable and all have 'Moobius' in the title")
    public void keyPagesAreReachableAndHaveMoobiusInTitle() {
        String[][] pages = {
                {GIFTS_URL, "gifts page"},
                {ABOUT_URL, "about us page"},
                {CATALOG_URL, "catalog page"},
                {EBOOK_URL, "ebook page"},
                {BOARD_GAME_URL, "board game page"}
        };

        for (String[] page : pages) {
            driver.get(page[0]);
            String title = driver.getTitle();
            Assert.assertTrue(title.contains("Moobius"),
                    "The " + page[1] + " should have 'Moobius' in its title, but was: " + title);
        }
    }

    @Test(description = "Browser back and forward navigation works between pages")
    public void browserBackAndForwardNavigationWorks() {
        driver.get(ConfigReader.get("base.url"));
        driver.get(CATALOG_URL);

        driver.navigate().back();
        Assert.assertTrue(driver.getCurrentUrl().contains("moobius.hu"),
                "Back navigation should stay on moobius.hu");

        driver.navigate().forward();
        Assert.assertTrue(driver.getCurrentUrl().contains("katalogus-nyito"),
                "Forward navigation should return to the catalog page");
    }

    @Test(description = "Hovering over the Könyvek nav item reveals a dropdown")
    public void hoveringOverBooksNavItemRevealsDropdown() {
        HomePage homePage = openHomePage();

        homePage.hoverOverBooksNavItem();

        List<WebElement> dropdownLinks = homePage.getDropdownLinksAfterHover();
        Assert.assertFalse(dropdownLinks.isEmpty(),
                "Hovering over the Könyvek nav item should reveal at least one dropdown link");
    }

    @Test(description = "About us page displays articles that mention 'Moobius'")
    public void aboutPageDisplaysMoobiusArticles() {
        HomePage homePage = new HomePage(driver);
        homePage.open(ABOUT_URL);

        List<String> titles = homePage.getAboutPageArticleTitles();

        Assert.assertFalse(titles.isEmpty(),
                "About us page should display at least one article");
        Assert.assertTrue(
                titles.stream().anyMatch(t -> t.contains("Moobius")),
                "At least one article title should contain 'Moobius'"
        );
    }
}
