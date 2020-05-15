package auto.framework.base;

import auto.framework.config.ConfigReader;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;

import java.net.URL;

public class EnvironmentSetUp {

    private ITestContext ctx;
    private String deviceName, platformName, platformVersion;

    public void BuildEnvironment(ITestContext ctx)
    {
        this.ctx = ctx;

        this.deviceName = ctx.getCurrentXmlTest().getParameter("deviceName");
        this.platformName = ctx.getCurrentXmlTest().getParameter("platformName");
        this.platformVersion = ctx.getCurrentXmlTest().getParameter("platformVersion");
    }

    public static AndroidDriver<MobileElement> LaunchAndroidAppTemp(ITestContext ctx, String devicename, String platformname, String platformversion) throws Exception {

        EnvironmentSetUp setup = new EnvironmentSetUp();
        setup.BuildEnvironment(ctx);

        AndroidDriver<MobileElement> driver;
        String testApp = ConfigReader.GetGlobalProperty("ANDROID_TEST_APP");
        String appPackage = ConfigReader.GetGlobalProperty("APP_PACKAGE");
        String appActivity = ConfigReader.GetGlobalProperty("APP_ACTIVITY");


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", devicename);
        capabilities.setCapability("platformName", platformname);
        capabilities.setCapability("platformVersion", platformversion);
        capabilities.setCapability("app", testApp);
        capabilities.setCapability("appActivity", appActivity);
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("automationName","UiAutomator2");
        capabilities.setCapability("autoGrantPermissions",true);

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);

        return driver;
    }
}
