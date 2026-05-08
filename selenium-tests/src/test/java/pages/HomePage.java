package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    private static final By LOGO = By.cssSelector("div.baseheader");
    private static final By BOOKS_NAV_ITEM = By.cssSelector("div.menu_divitem.menu_haschildmenu div.menu_divitem_click");
    private static final By NAV_DROPDOWN_LINKS = By.cssSelector("div.menu_childitems a");
    private static final By SEARCH_INPUT = By.xpath("//input[@id='search' or contains(@class, 'search-input')]");
    private static final By NEWSLETTER_SECTION = By.cssSelector("div.rb-section-footeritem-4");
    private static final By NEWSLETTER_NAME_INPUT = By.id("Name");
    private static final By NEWSLETTER_EMAIL_INPUT = By.id("EMail");
    private static final By NEWSLETTER_SUBMIT = By.cssSelector("button.submitbutton");
    private static final By NEWSLETTER_LABEL = By.xpath(".//label[@for='DataHandlingConfirmOk-newslettersubscribefooter']");
    private static final String CHECKBOX_ID = "DataHandlingConfirmOk-newslettersubscribefooter";
    private static final By ABOUT_ARTICLE_TITLES = By.xpath("//h2[contains(@class,'rbh1')]//span[contains(@class,'news_name')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {
        driver.get(baseUrl);
        waitForPageToLoad();
        acceptCookiesIfPresent();
    }

    public void hoverOverBooksNavItem() {
        hoverOver(BOOKS_NAV_ITEM);
    }

    public void searchFor(String term) {
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT));
        input.clear();
        input.sendKeys(term);
        input.sendKeys(org.openqa.selenium.Keys.ENTER);
        wait.until(ExpectedConditions.urlContains("talalati-lista"));
    }

    public void fillAndSubmitNewsletterForm(String name, String email) {
        WebElement section = wait.until(ExpectedConditions.presenceOfElementLocated(NEWSLETTER_SECTION));
        scrollToElement(section);

        WebElement nameField  = section.findElement(NEWSLETTER_NAME_INPUT);
        WebElement emailField = section.findElement(NEWSLETTER_EMAIL_INPUT);
        WebElement checkbox   = section.findElement(By.id(CHECKBOX_ID));
        WebElement label      = section.findElement(NEWSLETTER_LABEL);
        WebElement submit     = section.findElement(NEWSLETTER_SUBMIT);

        nameField.clear();
        nameField.sendKeys(name);
        emailField.clear();
        emailField.sendKeys(email);

        if (!checkbox.isSelected()) {
            label.click();
        }

        submit.click();
    }

    public boolean isNewsletterNormalCheckboxChecked() {
        return (Boolean) js.executeScript(
                "return document.getElementById('" + CHECKBOX_ID + "').checked;");
    }

    public boolean isLogoDisplayed() {
        return waitForVisible(LOGO).isDisplayed();
    }

    public List<WebElement> getDropdownLinksAfterHover() {
        return findAll(NAV_DROPDOWN_LINKS);
    }

    public List<String> getAboutPageArticleTitles() {
        return findAll(ABOUT_ARTICLE_TITLES)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
