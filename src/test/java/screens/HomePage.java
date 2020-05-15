package screens;

import auto.framework.base.AndroidGestures;
import auto.framework.base.Base;
import auto.framework.base.UIElements;
import auto.framework.utilities.ExcelUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class HomePage extends Base {
    private AndroidDriver<MobileElement> driver;
    private String strTestCaseName;

    public HomePage(AndroidDriver<MobileElement> driver, String strTestCaseName) {
        this.driver = driver;
        this.strTestCaseName = strTestCaseName;
    }

    public void TapHamburgerMenu() throws Exception {
        AndroidGestures.Tap(driver, "mnuHamburgerMenu", 2);
    }

    public void TapSignIn() throws Exception {
        UIElements.WaitTillVisible(driver,"lblSignIn", 5 );
        AndroidGestures.Tap(driver, "lblSignIn", 2);
    }

    public void TapSearchTextBox() throws Exception {
        UIElements.WaitTillVisible(driver,"txtSearchBox", 10 );
        AndroidGestures.Tap(driver, "txtSearchBox", 5);
    }

    public void SearchItem() throws Exception {
        String searchItem = ExcelUtil.GetCellData("SearchText", strTestCaseName);

        UIElements.WaitTillVisible(driver,"txtSearchBox_TextBox", 5 );
        UIElements.EnterText(driver, "txtSearchBox_TextBox", searchItem, 2);

        UIElements.WaitTillVisible(driver,"lblFirstRelatedSearchItem", 5 );
        AndroidGestures.Tap(driver, "lblFirstRelatedSearchItem", searchItem, 5);
    }
}