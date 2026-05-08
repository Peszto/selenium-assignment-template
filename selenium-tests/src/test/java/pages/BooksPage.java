package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class BooksPage extends BasePage {

    private static final By PRODUCT_CARDS = By.cssSelector("div.alexwebdatainfogrid");
    private static final By FIRST_PRODUCT = By.xpath(
            "(//div[contains(@class,'alexwebdatainfogrid')]//h2[contains(@class,'alexdata_header_name')])[1]"
    );
    private static final By SORT_DROPDOWN = By.id("rb-section-allproductheader-sortcombo");

    public BooksPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {
        driver.get(baseUrl + "/konyvek");
        waitForPageToLoad();
        acceptCookiesIfPresent();
    }

    public void sortBy(String visibleText) {
        WebElement firstProduct = waitForVisible(FIRST_PRODUCT);
        WebElement dropdown = waitForVisible(SORT_DROPDOWN);
        new Select(dropdown).selectByVisibleText(visibleText);
        wait.until(ExpectedConditions.stalenessOf(firstProduct));
        waitForVisible(FIRST_PRODUCT);
    }

    public List<String> getSortOptions() {
        return new Select(waitForVisible(SORT_DROPDOWN)).getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getSelectedSortOption() {
        return new Select(waitForVisible(SORT_DROPDOWN)).getFirstSelectedOption().getText();
    }

    public int getProductCount() {
        return findAll(PRODUCT_CARDS).size();
    }
}
