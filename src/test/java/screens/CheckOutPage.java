package screens;

import auto.framework.base.AndroidGestures;
import auto.framework.base.Helper;
import auto.framework.base.UIElements;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;

public class CheckOutPage {
    private AndroidDriver<MobileElement> driver;
    private String strTestCaseName;

    private String itemDesc;
    private String itemPrice;
    private String itemQuantity;
    private String itemShippingPrice;
    private String itemSubtotalPrice;
    private String itemSubtotalShippingPrice;
    private String itemTotalPrice;

    public CheckOutPage(AndroidDriver<MobileElement> driver, String strTestCaseName) {
        this.driver = driver;
        this.strTestCaseName = strTestCaseName;
    }

    public HashMap<String, String> SaveProductDetailsOnCheckOutPage() throws Exception {
        UIElements.WaitTillVisible(driver,"lblItemDescription", 10 );
        MobileElement eleItemDesc = UIElements.FetchElement(driver,"lblItemDescription", 5);
        itemDesc = eleItemDesc.getText();

        MobileElement eleItemPrice = UIElements.FetchElement(driver,"lblItemPrice", 5);
        itemPrice = Helper.RemoveCharactersFromDoubleString(eleItemPrice.getText());

        MobileElement eleItemQuantity = UIElements.FetchElement(driver,"lblItemQuantity", 5);
        itemQuantity = eleItemQuantity.getText();

        MobileElement eleItemShippingPrice = UIElements.FetchElement(driver,"lblItemPostagePrice", 5);
        itemShippingPrice = Helper.RemoveCharactersFromDoubleString(eleItemShippingPrice.getText());

        AndroidGestures.ScrollIntoView(driver, "lblItemTotalPrice", 5, "UP");

        MobileElement eleItemSubtotalPrice = UIElements.FetchElement(driver,"lblItemSubTotalPrice", 5);
        itemSubtotalPrice = Helper.RemoveCharactersFromDoubleString(eleItemSubtotalPrice.getText());

        MobileElement eleItemSubtotalShippingPrice = UIElements.FetchElement(driver,"lblItemTotalPostagePrice", 5);
        itemSubtotalShippingPrice = Helper.RemoveCharactersFromDoubleString(eleItemSubtotalShippingPrice.getText());

        MobileElement eleItemTotalPrice = UIElements.FetchElement(driver,"lblItemTotalPrice", 5);
        itemTotalPrice = Helper.RemoveCharactersFromDoubleString(eleItemTotalPrice.getText());

        HashMap<String, String> prodDetailsCheckOutPage = new HashMap<>();
        prodDetailsCheckOutPage.put("Product Description", itemDesc);
        prodDetailsCheckOutPage.put("Product Price", itemPrice);
        prodDetailsCheckOutPage.put("Product Shipping Price", itemShippingPrice);
        prodDetailsCheckOutPage.put("Product Quantity", itemQuantity);
        prodDetailsCheckOutPage.put("Product SubTotal Price", itemSubtotalPrice);
        prodDetailsCheckOutPage.put("Product SubTotal Shipping Price", itemSubtotalShippingPrice);
        prodDetailsCheckOutPage.put("Product Total Price", itemTotalPrice);

        return prodDetailsCheckOutPage;
    }
}