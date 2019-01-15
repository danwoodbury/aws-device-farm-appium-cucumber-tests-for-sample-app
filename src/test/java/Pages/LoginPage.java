package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    private static final int KEYBOARD_ANIMATION_DELAY = 1000;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='LoginSubmitButton']")
    private MobileElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='LoginEmailField']")
    private MobileElement usernameField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='LoginEmailError']")
    private MobileElement emailError;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='LoginPasswordField']")
    private MobileElement passwordField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='LoginPasswordError']")
    private MobileElement passwordError;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Sign In Failed']")
    private MobileElement loginFailureAlert;

    @AndroidFindBy(id = "android:id/button1")
    private MobileElement tryAgainButton;

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean enterUsernameAndPassword(String username, String password) throws InterruptedException {
        boolean usernameStatus = sendKeysToElement(username, usernameField, false);

        passwordField.click();
        Thread.sleep(KEYBOARD_ANIMATION_DELAY);
        passwordField.sendKeys(password);

        return usernameStatus;
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public boolean checkInvalidLoginAlertExists() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        MobileElement introscreen1 = (MobileElement) wait.until(ExpectedConditions.visibilityOf(loginFailureAlert));

        return loginFailureAlert.isDisplayed();
    }

    public void clickLoginTryAgain() {
        this.checkInvalidLoginAlertExists();
        tryAgainButton.click();
    }

    public boolean checkIfBackAtLogin() {
        return loginButton.isDisplayed() && usernameField.isDisplayed() && passwordField.isDisplayed();
    }

    public String getEmailErrorMessage() {
        return emailError.getText();
    }

    public String getPasswordErrorMessage() {
        return passwordError.getText();
    }
}
