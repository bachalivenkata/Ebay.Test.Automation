package screens;

import auto.framework.base.AndroidGestures;
import auto.framework.base.UIElements;
import auto.framework.utilities.ExcelUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class SearchResultsPage {
    private AndroidDriver<MobileElement> driver;
    private String strTestCaseName;

    public SearchResultsPage(AndroidDriver<MobileElement> driver, String strTestCaseName) {
        this.driver = driver;
        this.strTestCaseName = strTestCaseName;
    }

    public void SelectRandomItem() throws Exception {
        AndroidGestures.Tap(driver, "lblSaveNotification", 10);

        String index = ExcelUtil.GetCellData("SelectSearchResultsItemIndex", strTestCaseName);
        int searchItemIndex = Integer.parseInt(index);

        String textValue = ExcelUtil.GetCellData("SelectSearchResultsItemText", strTestCaseName);
        UIElements.WaitTillVisible(driver,"lblSearchResultsList", textValue, 5 );
        AndroidGestures.TapOnListItems(driver, "lblSearchResultsList", textValue, searchItemIndex, 2);
    }
}
