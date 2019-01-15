package Tests.AbstractBaseTests;

import cucumber.api.testng.AbstractTestNGCucumberTests;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public abstract class TestBase extends AbstractTestNGCucumberTests {
    public static AndroidDriver<MobileElement> driver;

    @BeforeTest
    public abstract void setUpPage();

    @BeforeSuite
    public void setUpAppium() throws MalformedURLException {

        final String appName = "latest-android.apk";
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";

        String environment = System.getProperty("env");
        URL url = new URL(URL_STRING);

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (environment != null && environment.equals("local")) {
            //Set the DesiredCapabilities capabilities only for local development
            File f = new File("src");
            File fs = new File(f, appName);
            capabilities.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "Android Emulator");
            capabilities.setCapability("appPackage", "com.beautystack.app");
            capabilities.setCapability("appActivity", "com.beautystack.app.MainActivity");
            capabilities.setCapability("udid", "emulator-5554");
        }

        capabilities.setCapability("automationName", "uiautomator2");

        driver = new AndroidDriver<MobileElement>(url, capabilities);

        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void tearDownAppium() {
        driver.quit();
    }

    @AfterClass
    public void restartApp() {
        driver.resetApp();
    }
}
