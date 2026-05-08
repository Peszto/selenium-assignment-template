package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private static final By COMMENT_TEXTAREA = By.cssSelector("textarea[name='description']");
    private static final By COMMENT_TAB = By.xpath("//div[contains(@class,'rb-tabbed-master-item-name')]//p[contains(text(),'Hozzászólások')]");

    private final String url;

    public ProductPage(WebDriver driver, String url) {
        super(driver);
        this.url = url;
    }

    @Override
    public void open() {
        driver.get(url);
        waitForPageToLoad();
        acceptCookiesIfPresent();
    }

    public void switchToCommentsTab() {
        waitForVisible(COMMENT_TAB).click();
    }

    public boolean isCommentTextareaPresent() {
        return isPresentWithShortWait(COMMENT_TEXTAREA);
    }

    public String getCommentPlaceholder() {
        wait.until(ExpectedConditions.presenceOfElementLocated(COMMENT_TEXTAREA));
        return driver.findElement(COMMENT_TEXTAREA).getAttribute("placeholder");
    }

}
