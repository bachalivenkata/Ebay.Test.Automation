package screens;

import auto.framework.base.AndroidGestures;
import auto.framework.base.Helper;
import auto.framework.base.UIElements;
import auto.framework.utilities.ExcelUtil;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;

import java.util.HashMap;

public class ProductDetailsPage {
    private AndroidDriver<MobileElement> driver;
    private String strTestCaseName;

    private static String prodDesc;
    private static String prodPrice;
    private static String prodShipPrice;
    private static String prodQuantity;
    private static String prodTotalPrice;

    public ProductDetailsPage(AndroidDriver<MobileElement> driver, String strTestCaseName) {
        this.driver = driver;
        this.strTestCaseName = strTestCaseName;
    }

    public HashMap<String, String> SaveProductDetails() throws Exception {
        HashMap<String, String> prodDetails = new HashMap<>();

        UIElements.WaitTillVisible(driver,"lblProductDescription", 10 );
        MobileElement eleProdDesc = UIElements.FetchElement(driver,"lblProductDescription", 5);
        prodDesc = eleProdDesc.getText();

        MobileElement eleProdPrice = UIElements.FetchElement(driver,"lblProductPrice", 5);
        prodPrice = Helper.RemoveCharactersFromDoubleString(eleProdPrice.getText());

        if(UIElements.IsElementExist(driver,"lblProductQuantity", 5 )){
            MobileElement eleProdQuantity = UIElements.FetchElement(driver,"lblProductQuantity", 5);
            prodQuantity = eleProdQuantity.getText();

            SelectProductQuantity();

            //Saving Product Quantity after changing the list item
            MobileElement eleProdQuantity1 = UIElements.FetchElement(driver,"lblProductQuantity", 5);
            prodQuantity = eleProdQuantity1.getText();
        }
        else
        {
            prodQuantity = "1";
        }

        MobileElement eleProdShipPrice = UIElements.FetchElement(driver,"lblProductShippingPrice", 5);
        prodShipPrice = Helper.RemoveCharactersFromDoubleString(eleProdShipPrice.getText());

        Double dbProdTotalPrice = Double.valueOf(prodPrice)*Integer.parseInt(prodQuantity) + Double.valueOf(prodShipPrice);
        prodTotalPrice = String.valueOf(dbProdTotalPrice);

        double subtotalPrice = Double.valueOf(prodPrice)*Integer.parseInt(prodQuantity);
        double subtotalShippingPrice = Double.valueOf(prodShipPrice)*Integer.parseInt(prodQuantity);

        prodDetails.put("Product Description", prodDesc);
        prodDetails.put("Product Price", prodPrice);
        prodDetails.put("Product Shipping Price", prodShipPrice);
        prodDetails.put("Product Quantity", prodQuantity);
        prodDetails.put("Product SubTotal Price", String.valueOf(subtotalPrice));
        prodDetails.put("Product SubTotal Shipping Price", String.valueOf(subtotalShippingPrice));
        prodDetails.put("Product Total Price", prodTotalPrice);

        return prodDetails;
    }

    public void SelectProductQuantity() throws Exception {
        String expProdQuantity = ExcelUtil.GetCellData("ProductQuantity", strTestCaseName);

        MobileElement eleProdQuantity = UIElements.FetchElement(driver,"lblProductQuantity", 5);
        prodQuantity = eleProdQuantity.getText();

        if(!prodQuantity.equals(expProdQuantity)){
            AndroidGestures.Tap(driver, "lblProductQuantity", 2);

            UIElements.WaitTillVisible(driver,"lblProductQuanityList", 5 );
            AndroidGestures.TapOnListItems(driver, "lblProductQuanityList", Integer.parseInt(expProdQuantity), 2);
        }
    }

    public void BuyProduct() throws Exception {
        if(UIElements.IsElementExist(driver,"lblProductQuantity", 5 )){
            SelectProductQuantity();
        }
        else
        {
            String expProdQuantity = ExcelUtil.GetCellData("ProductQuantity", strTestCaseName);
            if(Integer.parseInt(expProdQuantity) != 1){
                Assert.fail("Expected Product Quantity :'" + expProdQuantity + "' is not available");
            }
        }
        AndroidGestures.Tap(driver, "btnBuyNow", 2);
    }
}
