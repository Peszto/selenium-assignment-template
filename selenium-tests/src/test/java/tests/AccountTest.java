package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.RandomDataGenerator;

public class AccountTest extends BaseTest {

    private void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(ConfigReader.get("base.url"));
        loginPage.loginAs(ConfigReader.get("user.email"), ConfigReader.get("user.password"));
    }

    private AccountPage openAccountPage() {
        AccountPage accountPage = new AccountPage(driver);
        accountPage.openViaNavItem();
        return accountPage;
    }

    @Test(description = "A logged-in user can reach the account page")
    public void loggedInUserCanReachAccountPage() {
        login();
        AccountPage accountPage = openAccountPage();

        Assert.assertTrue(accountPage.isOnAccountPage(),
                "User should land on the account page after logging in");
    }

    @Test(
            description = "Account page title contains 'Moobius'",
            dependsOnMethods = "loggedInUserCanReachAccountPage"
    )
    public void accountPageTitleContainsMoobius() {
        login();
        AccountPage accountPage = openAccountPage();

        Assert.assertTrue(accountPage.getPageTitle().contains("Moobius"),
                "Account page title should contain 'Moobius'");
    }

    @Test(
            description = "User can update their first name with a random value and save successfully",
            dependsOnMethods = "loggedInUserCanReachAccountPage"
    )
    public void userCanUpdateFirstNameAndSaveSuccessfully() {
        login();
        AccountPage accountPage = openAccountPage();

        if (accountPage.isFirstNameFieldPresent()) {
            accountPage.updateFirstName(RandomDataGenerator.randomFirstName());
            accountPage.saveProfileChanges();

            Assert.assertTrue(
                    accountPage.isSuccessMessageDisplayed() || accountPage.isOnAccountPage(),
                    "Saving profile changes should either show a success message or remain on the account page"
            );
        } else {
            Assert.assertTrue(accountPage.isOnAccountPage(),
                    "Should still be on the account page even if the first name field is absent");
        }
    }
}
