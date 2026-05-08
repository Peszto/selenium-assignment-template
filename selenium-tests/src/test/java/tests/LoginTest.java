package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "User can log in with valid credentials")
    public void userCanLoginWithValidCredentials() {
        LoginPage loginPage = userActions.loginDefaultUser();

        Assert.assertTrue(loginPage.isLoggedIn(),
                "User should be logged in after successful login");
    }

    @Test(
            description = "Login popup shows an error with invalid credentials",
            dependsOnMethods = "userCanLoginWithValidCredentials"
    )
    public void loginPopupShowsErrorWithInvalidCredentials() {
        LoginPage loginPage = userActions.openLoginPage();
        loginPage.loginAs("invalid@example.com", "wrongpassword123");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "An error message should appear when credentials are invalid");
    }

    @Test(
            description = "User can log out and the login link reappears",
            dependsOnMethods = "userCanLoginWithValidCredentials"
    )
    public void userCanLogOutSuccessfully() {
        LoginPage loginPage = userActions.loginDefaultUser();

        loginPage.logout();

        Assert.assertTrue(loginPage.isLoggedOut(),
                "Login link should reappear in the header after logging out");
    }
}
