package tests;

import auto.framework.base.AndroidGestures;
import auto.framework.base.Base;
import auto.framework.base.Helper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import screens.*;

import java.util.HashMap;

public class PurchaseTVTest extends Base {
    private AndroidDriver<MobileElement> driver;
    ITestContext ctx;

    @Test
    public void PurachaseTV() throws Exception {
        ctx = GetCurrentTestContext();
        String strTestCaseName = ctx.getCurrentXmlTest().getParameter("testName");
        driver = GetcurrentAndroidThreadDriver();

        HomePage home = new HomePage(driver, strTestCaseName);

        //Tapping on Hamburger menu icon
        home.TapHamburgerMenu();

        //Changing the screen orientation from Portrait to Landscape
        AndroidGestures.SetLandscapeOrientation(driver);
        home.TapSignIn();

        //Signing with Gmail account
        SignInPage signIn = new SignInPage(driver, strTestCaseName);
        signIn.SignInWithGoogle();

        //Tapping on Search bar
        home.TapSearchTextBox();

        //Changing the screen orientation from Landscape to Portrait
        AndroidGestures.SetPortraitOrientation(driver);

        //Searching for an item based on the test data
        home.SearchItem();

        //Selecting random item from search results
        SearchResultsPage searchResults = new SearchResultsPage(driver, strTestCaseName);
        searchResults.SelectRandomItem();

        ProductDetailsPage prodDetailsPage = new ProductDetailsPage(driver, strTestCaseName);
        //Saving product details on product search page
        HashMap<String, String> searchScreenProdDetails = prodDetailsPage.SaveProductDetails();

        //Adding product quantity and purchasing product
        prodDetailsPage.BuyProduct();

        //Saving product details on checkout page
        CheckOutPage checkOutPage = new CheckOutPage(driver, strTestCaseName);
        HashMap<String, String> checkOutPageProdDetails = checkOutPage.SaveProductDetailsOnCheckOutPage();

        //Comparing and asserting product details on checkout page against product search page
        CompareProductDetailsAgainstCheckOutPageDetails(searchScreenProdDetails, checkOutPageProdDetails);
    }

    public void CompareProductDetailsAgainstCheckOutPageDetails(HashMap<String, String> searchScreenProdDetails, HashMap<String, String> checkOutPageProdDetails) throws Exception {
        Helper.AssertText("Product Description", searchScreenProdDetails.get("Product Description"), checkOutPageProdDetails.get("Product Description"));
        Helper.AssertText("Product Price", searchScreenProdDetails.get("Product Price"), checkOutPageProdDetails.get("Product Price"));
        Helper.AssertText("Product Shipping Price", searchScreenProdDetails.get("Product Shipping Price"), checkOutPageProdDetails.get("Product Shipping Price"));
        Helper.AssertText("Product Quantity", searchScreenProdDetails.get("Product Quantity"), checkOutPageProdDetails.get("Product Quantity"));
        Helper.AssertText("Product SubTotal Price", searchScreenProdDetails.get("Product SubTotal Price"), checkOutPageProdDetails.get("Product SubTotal Price"));
        Helper.AssertText("Product SubTotal Shipping Price", searchScreenProdDetails.get("Product SubTotal Shipping Price"), checkOutPageProdDetails.get("Product SubTotal Shipping Price"));
        Helper.AssertText("Product Total Price", searchScreenProdDetails.get("Product Total Price"), checkOutPageProdDetails.get("Product Total Price"));
    }
}
