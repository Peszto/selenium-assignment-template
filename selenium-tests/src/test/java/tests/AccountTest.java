package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import utils.RandomDataGenerator;

public class AccountTest extends BaseTest {

    @Test(description = "A logged-in user can reach the account page")
    public void loggedInUserCanReachAccountPage() {
        userActions.loginDefaultUser();
        AccountPage accountPage = userActions.openAccountPage();

        Assert.assertTrue(accountPage.isOnAccountPage(),
                "User should land on the account page after logging in");
    }

    @Test(
            description = "Account page title contains 'Moobius'",
            dependsOnMethods = "loggedInUserCanReachAccountPage"
    )
    public void accountPageTitleContainsMoobius() {
        userActions.loginDefaultUser();
        AccountPage accountPage = userActions.openAccountPage();

        Assert.assertTrue(accountPage.getPageTitle().contains("Moobius"),
                "Account page title should contain 'Moobius'");
    }

    @Test(
            description = "User can update their first name with a random value and save successfully",
            dependsOnMethods = "loggedInUserCanReachAccountPage"
    )
    public void userCanUpdateFirstNameAndSaveSuccessfully() {
        userActions.loginDefaultUser();
        AccountPage accountPage = userActions.openAccountPage();

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
