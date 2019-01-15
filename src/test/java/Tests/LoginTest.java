package Tests;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.After;

import Pages.IntroPage;
import Pages.LoginPage;
import Tests.AbstractBaseTests.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@CucumberOptions(
        strict = true,
        monochrome = true,
        features = "classpath:LoginTest",
        plugin = {"pretty"}
)
public class LoginTest extends TestBase {
    private static final String CLIENT_VALID_EMAIL = "28@test.com";
    private static final String CLIENT_VALID_PASSWORD = "password1";
    private static final String CLIENT_INVALID_EMAIL = "Invalid Email";
    private static final String CLIENT_INVALID_PASSWORD = "passwor";
    private static final String PRO_VALID_EMAIL = "august@test.com";
    private static final String PRO_VALID_PASSWORD = "password1";
    private static final String LOGIN_EMPTY_EMAIL_ERROR_MESSAGE = "Please enter your email address";
    private static final String LOGIN_EMPTY_PASSWORD_ERROR_MESSAGE = "Please enter your password";
    private static final String LOGIN_INVALID_EMAIL_ERROR_MESSAGE = "Please enter a valid email address";

    private IntroPage introPage;
    private LoginPage loginPage;

    @Given("^I navigate to the login page$")
    public void setUpPage() {
        introPage = new IntroPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Given("^the ([^\"]*) user enters a ([^\"]*) username and password$")
	public void loginAttempt(String usertype, String validity) throws InterruptedException {
        if (usertype.equals("client") && validity.equals("valid")) {
            Assert.assertTrue(loginPage.enterUsernameAndPassword(CLIENT_VALID_EMAIL, CLIENT_VALID_PASSWORD));
		} else if (usertype.equals("pro") && validity.equals("valid")) {
            Assert.assertTrue(loginPage.enterUsernameAndPassword(PRO_VALID_EMAIL, PRO_VALID_PASSWORD));
		} else if (usertype.equals("client") && validity.equals("invalid")) {
            Assert.assertTrue(loginPage.enterUsernameAndPassword(CLIENT_VALID_EMAIL, CLIENT_INVALID_PASSWORD));
		}
    }

    @Given("^the user enters an invalid ([^\"]*)$")
	public void enterInvalidDetails(String field) throws InterruptedException {
        if (field.equals("email")) {
            Assert.assertTrue(loginPage.enterUsernameAndPassword(CLIENT_INVALID_EMAIL, CLIENT_VALID_PASSWORD));
        } else if (field.equals("password")) {
            Assert.assertTrue(loginPage.enterUsernameAndPassword(CLIENT_VALID_EMAIL, CLIENT_INVALID_PASSWORD));
        }
    }

    @And("^the user clicks on the login button$")
    public void clickLogin() throws InterruptedException {
        loginPage.clickLoginButton();
    }

    @And("^the user clicks on the try again button$")
	public void clickTryAgainButton() throws InterruptedException {
		loginPage.clickLoginTryAgain();
	}

    @Then("^the login screen is displayed$")
	public void checkIfOnLoginPage() throws InterruptedException {
        Assert.assertTrue(loginPage.checkIfBackAtLogin());
    }

    @Then("^the ([^\"]*) user is successfully logged in$")
	public void successfulLogin(String usertype) throws InterruptedException {
        introPage.cycleThroughScreens();
        Assert.assertTrue(introPage.isOnIntro());
    }

    @Then("^the error messages are displayed$")
	public void checkErrorMessages() throws InterruptedException {
        Assert.assertEquals(loginPage.getEmailErrorMessage(), LOGIN_EMPTY_EMAIL_ERROR_MESSAGE);
        Assert.assertEquals(loginPage.getPasswordErrorMessage(), LOGIN_EMPTY_PASSWORD_ERROR_MESSAGE);
    }

    @Then("^the invalid email error message is displayed$")
	public void checkEmailErrorMessages() throws InterruptedException {
        Assert.assertEquals(loginPage.getEmailErrorMessage(), LOGIN_INVALID_EMAIL_ERROR_MESSAGE);
    }

    @Then("^the invalid password error meesage is displayed$")
	public void checkPasswordErrorMessages() throws InterruptedException {
        Assert.assertTrue(loginPage.checkInvalidLoginAlertExists());
    }
    
    @After
    public void restartApp() {
        driver.resetApp();
    }
}
