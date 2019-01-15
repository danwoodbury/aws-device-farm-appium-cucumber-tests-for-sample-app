package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IntroPage extends BasePage {
    private static final int SWIPE_DELAY = 2000;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='IntroScreen1']")
    private MobileElement screen1;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='IntroScreen2']")
    private MobileElement screen2;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='IntroScreen3']")
    private MobileElement screen3;

    public IntroPage(AppiumDriver driver) {
        super(driver);
    }

    public void cycleThroughScreens() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        MobileElement introscreen1 = (MobileElement) wait.until(ExpectedConditions.visibilityOf(screen1));
    }

    public boolean isOnIntro() {
        return screen1.isDisplayed() || screen2.isDisplayed() || screen3.isDisplayed();
    }
}
