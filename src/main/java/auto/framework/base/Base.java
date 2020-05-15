package auto.framework.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Base
{
    public AppiumDriver<MobileElement> driver;
    public static HashMap<Thread, AppiumDriver<MobileElement>> drivers = new HashMap<Thread, AppiumDriver<MobileElement>>();
    public static ITestContext ctx;

    public String deviceName;
    public String platformName;
    public String platformVersion;

    public static Logger logger = LogManager.getLogger(auto.framework.base.Base.class.getName());

    @BeforeTest
    public void oneTime(ITestContext ctx) throws Exception {
        this.ctx = ctx;
        LaunchAppSession();
    }


    public void LaunchAppSession() throws Exception {
        this.ctx = ctx;
        platformName = ctx.getCurrentXmlTest().getParameter("platformName");
        deviceName=ctx.getCurrentXmlTest().getParameter("deviceName");
        platformVersion=ctx.getCurrentXmlTest().getParameter("platformVersion");

        driver = EnvironmentSetUp.LaunchAndroidAppTemp(ctx, deviceName, platformName, platformVersion);
        drivers.put(Thread.currentThread(), driver);
    }

    public static void LogMessage(String message) {
        logger.info("[" + new SimpleDateFormat("HH:mm:ss:SSS").format((new Date())) + "] [Thread ID : " + Thread.currentThread().getId() + "] " + message, true);
    }

    public static AndroidDriver<MobileElement> GetcurrentAndroidThreadDriver() throws Exception {
        LogMessage("Current driver value: " + drivers.get(Thread.currentThread()));
        return (AndroidDriver<MobileElement>) drivers.get(Thread.currentThread());
    }

    public ITestContext GetCurrentTestContext(){
        return ctx;
    }
}