package auto.framework.base;

import auto.framework.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.util.List;

public class UIElements {

    public static By GetProperties(String property) throws Exception {
        try {
            String element = ConfigReader.GetUIElementsProperty(property);
            if (element != null) {
                String[] params = ConfigReader.GetUIElementsProperty(property).split(",");
                String newIdentifier = params[0] + ",";
                String newValue = element.replace(newIdentifier, "");
                if (params[0] != null || newValue != null) {
                    if (params[0].equalsIgnoreCase("accessibilityid")) {
                        Class<MobileBy> cl = MobileBy.class;
                        Method m1 = cl.getMethod(params[0], String.class);
                        MobileBy.ByAccessibilityId s1 = (MobileBy.ByAccessibilityId) m1.invoke(null, newValue);
                        return s1;

                    } else {
                        Class<By> cl = By.class;
                        Method m1 = cl.getMethod(params[0], String.class);

                        By s1 = (By) m1.invoke(null, newValue);
                        return s1;
                    }
                } else {
                    Base.LogMessage("MobileElement: " + property + " format is not proper in the property file");
                    throw new Exception("MobileElement: " + property + " format is not proper in the property file");
                }
            } else {
                //Base.logMessage("MobileElement: " + property + " not found in the property file");
                throw new Exception("MobileElement: " + property + " not found in the property file");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static By GetProperties(String property, String dynamicValue) throws Exception {
        try {
            String element = ConfigReader.config.getProperty(property);
            if (element != null) {
                String[] params = ConfigReader.config.getProperty(property).split(",");
                String newIdentifier = params[0] + ",";
                String newValue = element.replace(newIdentifier, "");

                if (newValue.contains("$value")) {
                    newValue = newValue.replace("$value", dynamicValue);
                } else {
                    Base.LogMessage("MobileElement: " + property + " does not contain $value");
                    throw new Exception("MobileElement: " + property + " does not contain $value");
                }

                if (params[0] != null || newValue != null) {
                    if (params[0].equalsIgnoreCase("accessibilityid")) {
                        Class<MobileBy> cl = MobileBy.class;
                        Method m1 = cl.getMethod(params[0], String.class);
                        MobileBy.ByAccessibilityId s1 = (MobileBy.ByAccessibilityId) m1.invoke(null, newValue);
                        return s1;
                    } else {
                        Class<By> cl = By.class;
                        Method m1 = cl.getMethod(params[0], String.class);
                        By s1 = (By) m1.invoke(null, newValue);
                        return s1;
                    }
                } else {
                    Base.LogMessage("MobileElement: " + property + " format is not proper in the property file");
                    throw new Exception("MobileElement: " + property + " format is not proper in the property file");
                }
            } else {
                Base.LogMessage("MobileElement: " + property + " not found in the property file");
                throw new Exception("MobileElement: " + property + " not found in the property file");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static MobileElement FetchElement(AppiumDriver<MobileElement> driver, String object, long time) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time, 100);
            wait.ignoring(org.openqa.selenium.NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(UIElements.GetProperties(object)));

            MobileElement element = driver.findElement(UIElements.GetProperties(object));
            return element;
        } catch (Exception e) {
            Base.LogMessage("MobileElement " + object + " not found");
            throw new Exception("Unable to locate element: " + object + e);
        }
    }

    public static MobileElement FetchElement(AppiumDriver<MobileElement> driver, String object, String dynamicValue, long time) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time, 100);
            wait.ignoring(org.openqa.selenium.NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(UIElements.GetProperties(object, dynamicValue)));

            MobileElement element = driver.findElement(UIElements.GetProperties(object, dynamicValue));
            return element;
        } catch (Exception e) {
            Base.LogMessage("MobileElement " + object + " not found");
            throw new Exception("Unable to locate element: " + object  + e);
        }
    }

    public static List<MobileElement> FetchElements(AppiumDriver<MobileElement> driver, String locatorName, long time) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time, 100);
            wait.ignoring(org.openqa.selenium.NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(UIElements.GetProperties(locatorName)));

            List<MobileElement> elements = driver.findElements(UIElements.GetProperties(locatorName));
            return elements;
        } catch (Exception e) {
            Base.LogMessage("MobileElement " + locatorName + " not found");
            throw new Exception("Unable to locate element: " + locatorName + e);
        }
    }

    public static List<MobileElement> FetchElements(AppiumDriver<MobileElement> driver, String locatorName, String dynamicLocatorValue, long time) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time, 100);
            wait.ignoring(org.openqa.selenium.NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(UIElements.GetProperties(locatorName, dynamicLocatorValue)));

            List<MobileElement> elements = driver.findElements(UIElements.GetProperties(locatorName, dynamicLocatorValue));
            return elements;
        } catch (Exception e) {
            Base.LogMessage("MobileElement " + locatorName + " not found");
            throw new Exception("Unable to locate element: " + locatorName + e);
        }
    }

    public static void EnterText(AppiumDriver<MobileElement> driver, String locator, String setValue, long timevalue) throws Exception
    {
        MobileElement thisEle = null;
        try
        {
            thisEle = FetchElement(driver, locator,timevalue);
            thisEle.click();
            try { thisEle.clear(); // Some fields cannot be cleared, so clear on empty field can cause exception
            }catch(Exception e){}

            if(setValue != null) {
                thisEle.sendKeys(setValue);
            }

            Base.LogMessage("Set Edit Value on Locator : " + locator + " to :: " + setValue);

        }catch(Exception e)
        {
            Base.LogMessage("Unable to setValue on Element with Locator : " + locator + " to value :: " + setValue);
            throw new Exception("setEditValue failed");
        }
    }

    public static void EnterText(AppiumDriver<MobileElement> driver, String locator, String $dynamicLocatorValue, String setValue, long timevalue) throws Exception
    {
        MobileElement thisEle = null;
        try
        {
            if($dynamicLocatorValue != null)
                thisEle = FetchElement(driver, locator, $dynamicLocatorValue,10);
            else
                thisEle = FetchElement(driver, locator,timevalue);

            thisEle.click();
            try { thisEle.clear(); // Some fields cannot be cleared, so clear on empty field can cause exception
            }catch(Exception e){}

            if(setValue != null) {
                thisEle.sendKeys(setValue);
            }

            Base.LogMessage("Set Edit Value on Locator : " + locator + " to :: " + setValue);

        }catch(Exception e)
        {
            Base.LogMessage("Unable to setValue on Element with Locator : " + locator + " to value :: " + setValue);
            throw new Exception("setEditValue failed");
        }
    }

    public static Boolean WaitTillVisible(AppiumDriver<MobileElement> driver, String locator, long timevalue)
    {
        try {
            new WebDriverWait(driver, timevalue, 100).until(ExpectedConditions.visibilityOfElementLocated(UIElements.GetProperties(locator)));
            Base.LogMessage("waitTillVisible for locator " + locator + " completed");
            return true;
        }
        catch(Exception e)
        {
            Base.LogMessage("Element " + locator + " is NOT visible after waiting for " + timevalue + " Seconds");
            return false;
        }
    }

    public static Boolean WaitTillVisible(AppiumDriver<MobileElement> driver, String locator, String dynamicLocatorValue, long timevalue)
    {
        try {
            new WebDriverWait(driver, timevalue, 100).until(ExpectedConditions.visibilityOfElementLocated(UIElements.GetProperties(locator, dynamicLocatorValue)));
            Base.LogMessage("waitTillVisible for locator " + locator + " completed");
            return true;
        }
        catch(Exception e)
        {
            Base.LogMessage("Element " + locator + " is NOT visible after waiting for " + timevalue + " Seconds");
            return false;
        }
    }

    public static Boolean WaitTillVisible(AppiumDriver<MobileElement> driver, By thisBy, long timevalue)
    {
        try {
            new WebDriverWait(driver, timevalue, 100).until(ExpectedConditions.visibilityOfElementLocated(thisBy));
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static boolean IsElementExist(AppiumDriver<MobileElement> driver, String selector, long time) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time, 100);
            wait.ignoring(org.openqa.selenium.NoSuchElementException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(UIElements.GetProperties(selector)));
            return true;
        } catch (Exception e) {
            Base.LogMessage("MobileElement " + selector + " doesn't exist");
            return false;
        }
    }

    public static void HideKeyboard(AppiumDriver<MobileElement> driver) throws Exception {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            Base.LogMessage("Soft keyboard not appearing to hide!");
        }
    }
}
