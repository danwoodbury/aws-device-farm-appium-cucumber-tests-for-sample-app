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
    private static final String BROWSERSTACK_USERNAME = "danwoodbury4";
    private static final String BROWSERSTACK_KEY = "vVErutZbBoCP7o5nK1UK";
    private static final String PROPERTY_ENVIRONMENT = "env";
    private static final String ENVIRONMENT_BROWSERSTACK = "browserstack";
    private static final String ENVIRONMENT_LOCAL = "local";

    public static AndroidDriver<MobileElement> driver;

    @BeforeTest
    public abstract void setUpPage();

    @BeforeSuite
    public void beforeSuite() throws MalformedURLException {
        if (!this.isBrowserStack()) {
            this.setUpAppium();
        }
    }

    public void beforeScenario() throws MalformedURLException {
        if (this.isBrowserStack()) {
            this.setUpAppium();
        }
    }

    public void afterScenario() {
        if (this.isBrowserStack()) {
            driver.quit();
        } else {
            driver.resetApp();
        }
    }

    public void setUpAppium() throws MalformedURLException {
        final String LOCAL_APP_NAME = "latest-android.apk";
        final String URL_STRING = "http://127.0.0.1:4723/wd/hub";
        final String BROWSERSTACK_URL = "https://"+BROWSERSTACK_USERNAME+":"+BROWSERSTACK_KEY+"@hub-cloud.browserstack.com/wd/hub";

        String environment = System.getProperty("env");
        URL url = new URL(URL_STRING);

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (this.isLocal()) {
            //Set the DesiredCapabilities capabilities only for local development
            File f = new File("src");
            File fs = new File(f, LOCAL_APP_NAME);
            capabilities.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "Android Emulator");
            capabilities.setCapability("appPackage", "com.beautystack.app");
            capabilities.setCapability("appActivity", "com.beautystack.app.MainActivity");
            capabilities.setCapability("udid", "emulator-5554");
            capabilities.setCapability("automationName", "uiautomator2");
        } else if (this.isBrowserStack()) {
            //Set the DesiredCapabilities capabilities only for browserstack
            url = new URL(BROWSERSTACK_URL);
            capabilities.setCapability("device", "Google Pixel");
            capabilities.setCapability("os_version", "7.1");
            capabilities.setCapability("app", "bs://9d57dcc87a08d2c7486bc8d009dd0473af7d42d8");
        } else {
            capabilities.setCapability("automationName", "uiautomator2");
        }

        driver = new AndroidDriver<MobileElement>(url, capabilities);
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
    }

    private boolean isLocal() {
        String environment = System.getProperty(PROPERTY_ENVIRONMENT);
        return environment != null && environment.equals(ENVIRONMENT_LOCAL);
    }

    private boolean isBrowserStack() {
        String environment = System.getProperty(PROPERTY_ENVIRONMENT);
        return environment != null && environment.equals(ENVIRONMENT_BROWSERSTACK);
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
