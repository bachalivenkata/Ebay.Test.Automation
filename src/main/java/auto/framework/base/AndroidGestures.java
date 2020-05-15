package auto.framework.base;

import auto.framework.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AndroidGestures {

    //Tapping on element when locator is passed
    public static void Tap(AppiumDriver<MobileElement> driver, String locator, long timevalue) throws Exception
    {
        By thisBy = null;

        try {
            thisBy = UIElements.GetProperties(locator);

            new WebDriverWait(driver, timevalue, 1000).until(ExpectedConditions.elementToBeClickable(thisBy));
            new TouchAction(driver).tap(ElementOption.element(driver.findElement(thisBy))).release().perform();
        }
        catch(Exception e)
        {
            throw new Exception("[ERROR] Unable to Tap on " + locator);
        }
    }

    //Tapping on element when locator is passed along with dynamic value
    public static void Tap(AppiumDriver<MobileElement> driver, String locator, String $dynamicLocatorValue, long timevalue) throws Exception
    {
        By thisBy = null;

        try {
            thisBy = UIElements.GetProperties(locator, $dynamicLocatorValue);

            new WebDriverWait(driver, timevalue, 1000).until(ExpectedConditions.elementToBeClickable(thisBy));
            new TouchAction(driver).tap(ElementOption.element(driver.findElement(thisBy))).release().perform();
        }
        catch(Exception e)
        {
            throw new Exception("[ERROR] Unable to Tap on " + locator);
        }
    }

    //Tapping on element when Mobile Element is passed
    public static void Tap(AppiumDriver<MobileElement> driver, MobileElement element, long timevalue) throws Exception
    {
        try {
            new TouchAction(driver).tap(ElementOption.element(element)).release().perform();
        }
        catch(Exception e)
        {
            throw new Exception("[ERROR] Unable to Tap on " + element);
        }
    }

    //Tapping on List of elements when locator is passed
    public static void TapOnListItems(AppiumDriver<MobileElement> driver, String locator, int index, long timevalue) throws Exception
    {
        try {
            List<MobileElement> elementsList = UIElements.FetchElements(driver, locator,5);
            int i=1;
            for(MobileElement element : elementsList){
                if(index == i){
                    Tap(driver, element, timevalue);
                    break;
                }
                i++;
            }
        }
        catch(Exception e)
        {
            throw new Exception("[ERROR] Unable to Tap on " + locator + " and index " + index);
        }
    }

    //Tapping on list of elements when locator is passed along with dynamic value
    public static void TapOnListItems(AppiumDriver<MobileElement> driver, String locator, String dynamicLocatorValue, int index, long timevalue) throws Exception
    {
        try {
            List<MobileElement> elementsList = UIElements.FetchElements(driver, locator, dynamicLocatorValue, 5);
            int i=1;
            for(MobileElement element : elementsList){
                if(index == i){
                    Tap(driver, element, 2);
                    break;
                }
                i++;
            }
        }
        catch(Exception e)
        {
            throw new Exception("[ERROR] Unable to Tap on " + locator + " and index " + index);
        }
    }

    //To handle window swipe on device
    public static void WindowSwipe(AppiumDriver<MobileElement> driver, String swipeDirection) throws Exception
    {
        //To handle Screen size
        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = size.height / 2;
        int leftX = (int) (size.width * 0.10);
        int righX = (int) (size.width * 0.80);
        int upY = (int) (size.height * 0.70);
        int downY = (int) (size.height * 0.40);
        WaitOptions waitTime=WaitOptions.waitOptions(Duration.ofMillis(500));
        TouchAction swipe = null;

        switch(swipeDirection)
        {
            case "DOWN":
                swipe = new TouchAction(driver).press(PointOption.point(startX, downY)).waitAction(waitTime).moveTo(PointOption.point(startX, upY));
                break;
            case "UP":
                swipe = new TouchAction(driver).press(PointOption.point(startX, upY)).waitAction(waitTime).moveTo(PointOption.point(startX, downY));
                break;
            case "LEFT":
                swipe = new TouchAction(driver).press(PointOption.point(righX, startY)).waitAction(waitTime).moveTo(PointOption.point(leftX, startY));
                break;
            case "RIGHT":
                swipe = new TouchAction(driver).press(PointOption.point(leftX, startY)).waitAction(waitTime).moveTo(PointOption.point(righX, startY));
                break;
        }
        swipe.release().perform();
        Base.LogMessage("Swipe window completed");
    }


    //Scroll to an element
    public static void ScrollIntoView(AppiumDriver<MobileElement> driver, String locator, int limit, String direction) throws Exception {

        By thisBy = null;
        int i=0;

        thisBy = UIElements.GetProperties(locator);

        try {
            while(! UIElements.WaitTillVisible(driver, thisBy, 1) && i<limit)
            {
                WindowSwipe(driver, direction);
                i++;
            }

            Base.LogMessage("Scrolled to find Locator : " + locator);
        } catch (Exception e) {

            Base.LogMessage("MobileElement Locator : " + locator + " not found to be scrolled");
            throw e;
        }
    }

    public static void SetPortraitOrientation(AppiumDriver<MobileElement> driver) throws Exception {
        String orientation_flag = ConfigReader.GetGlobalProperty("SCREEN_ORIENTATION_TEST_FLAG");
        if(orientation_flag.equals("true")){
            if(driver.getOrientation().equals(ScreenOrientation.LANDSCAPE)) {
                driver.rotate(ScreenOrientation.PORTRAIT);
            }
        }
    }

    public static void SetLandscapeOrientation(AppiumDriver<MobileElement> driver) throws Exception {
        String orientation_flag = ConfigReader.GetGlobalProperty("SCREEN_ORIENTATION_TEST_FLAG");
        if(orientation_flag.equals("true")) {
            if (driver.getOrientation().equals(ScreenOrientation.PORTRAIT)) {
                driver.rotate(ScreenOrientation.LANDSCAPE);
            }
        }
    }
}
