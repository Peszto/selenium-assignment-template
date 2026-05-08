package logic;

import org.openqa.selenium.WebDriver;
import pages.*;
import utils.ConfigReader;

public class UserActions {

    private final WebDriver driver;

    public UserActions(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage openLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        return loginPage;
    }

    public LoginPage loginDefaultUser() {
        LoginPage loginPage = openLoginPage();
        loginPage.loginAs(ConfigReader.get("user.email"), ConfigReader.get("user.password"));
        return loginPage;
    }

    public HomePage openHomePage() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        return homePage;
    }

    public AccountPage openAccountPage() {
        AccountPage accountPage = new AccountPage(driver);
        accountPage.open();
        return accountPage;
    }

    public BooksPage openBooksPage() {
        BooksPage booksPage = new BooksPage(driver);
        booksPage.open();
        return booksPage;
    }

    public ProductPage openProductPage(String url) {
        ProductPage productPage = new ProductPage(driver, url);
        productPage.open();
        return productPage;
    }

}
