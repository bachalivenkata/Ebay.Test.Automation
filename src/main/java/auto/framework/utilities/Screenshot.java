package auto.framework.utilities;

import auto.framework.base.Base;
import auto.framework.config.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {

    public static void captureScreenShot(ITestResult result) {
    try {
        Base inst = (Base) result.getInstance();

        if (inst != null) {
            String myDevice = inst.deviceName;

            String myPlatform = inst.platformName;
            String myVersion = inst.platformVersion;
            String myTestNum = result.getName();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
            String time = df.format(new Date());

            String fileName = (myPlatform + "_" + myDevice + "_" + myVersion + "_" + myTestNum + "_" + time) + ".png";
            fileName = fileName.replaceAll("[^a-zA-Z0-9\\\\s\\\\.]","");
            result.setAttribute("screenshotLoc", fileName);

            WebDriver driverInstance = new Augmenter().augment(inst.driver);
            if (driverInstance != null) {

                File srcfile = null;

                try {
                    srcfile = ((TakesScreenshot) driverInstance).getScreenshotAs(OutputType.FILE);
                    Base.LogMessage("Taking screenshot for the failure");
                } catch (UnreachableBrowserException ex) {
                    ex.printStackTrace();
                }

                try {
                    if (srcfile != null) {
                        FileUtils.copyFile(srcfile, new File("file-screenshots" + File.separator + fileName));

                        String screenShotPath = ConfigReader.GetGlobalProperty("SCREENSHOT_PATH");
                        FileUtils.moveFile(srcfile, new File("file-screenshots" + File.separator + screenShotPath + File.separator + "Error_Screenshot.png"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Reporter.setCurrentTestResult(result);
                if (result.getThrowable() != null) {
                    Reporter.log(result.getThrowable().getMessage());
                }
                Reporter.log("<a href="+fileName+">Click to open failure screenshot</a>");
            } else {
                Base.LogMessage("Driver not exist. Screenshot cannot be taken !!");
            }
        } else {
            Base.LogMessage("Driver not exist. Screenshot cannot be taken !!");
        }
    } catch (Exception e) {
        Base.LogMessage("Unable to capture screenshot due to exception: " + e);
    }
}
}
