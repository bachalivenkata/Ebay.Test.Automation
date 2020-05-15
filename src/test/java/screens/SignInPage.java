package screens;

import auto.framework.base.AndroidGestures;
import auto.framework.base.Base;
import auto.framework.base.UIElements;
import auto.framework.utilities.ExcelUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;

public class SignInPage extends Base {
    private AndroidDriver<MobileElement> driver;
    private String strTestCaseName;

    public SignInPage(AndroidDriver<MobileElement> driver, String strTestCaseName) {
        this.driver = driver;
        this.strTestCaseName = strTestCaseName;
    }

    public void SignInWithGoogle() throws Exception {
        String userName = ExcelUtil.GetCellData("UserName", strTestCaseName);
        String passWord = ExcelUtil.GetCellData("Password", strTestCaseName);

        UIElements.WaitTillVisible(driver,"btnSignInEbay", 5 );
        AndroidGestures.Tap(driver, "btnSignInEbay", 2);

        UIElements.WaitTillVisible(driver,"txtUserName", 5 );
        UIElements.EnterText(driver, "txtUserName", userName, 2);
        UIElements.HideKeyboard(driver);

        //UIElements.EnterText(driver, "txtPassword", passWord, 2);
        //UIElements.HideKeyboard(driver);
        AndroidGestures.Tap(driver, "btnSignIn", 2);
    }
}