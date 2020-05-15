package auto.framework.base;

import org.testng.Assert;

public class Helper extends Base{

    //Verifying the assertion when Strings are passed
    public static void AssertText(String fieldName, String actvalue, String expvalue) throws Exception {
        try{
            if (actvalue.equalsIgnoreCase(expvalue))
            {
                LogMessage(fieldName + " - Assert Text passed! Expected value is: " + expvalue);
            }
            else if (actvalue.contains(expvalue))
            {
                LogMessage(fieldName + " - Assert Text passed! Expected value is: " + expvalue);
            }
            else
            {
                Assert.fail(fieldName + " - Actual value not equals expected value. Expected: " + expvalue + ", Actual: " + actvalue);
            }
        }catch (Exception e)
        {
            Base.LogMessage(String.format(fieldName + " - Actual value not equals expected value. Expected: " + expvalue + ", Actual: " + actvalue));
            throw new Exception(e);
        }
    }

    //Verifying the assertion when integers are passed
    public static void AssertText(String fieldName, int actvalue, int expvalue) throws Exception {
        try{
            if (actvalue == expvalue)
            {
                LogMessage(fieldName + " - Assert Text passed! Expected value is: " + expvalue);
            }
            else
            {
                Assert.fail(fieldName + " - Actual value not equals expected value. Expected: " + expvalue + ", Actual: " + actvalue);
            }
        }catch (Exception e)
        {
            Base.LogMessage(String.format(fieldName + " - Actual value not equals expected value. Expected: " + expvalue + ", Actual: " + actvalue));
            throw new Exception(e);
        }
    }

    //Verifying the assertion when double values are passed
    public static void AssertText(String fieldName, Double actvalue, Double expvalue) throws Exception {
        try{
            if (actvalue == expvalue)
            {
                LogMessage(fieldName + " - Assert Text passed! Expected value is: " + expvalue);
            }
            else
            {
                Assert.fail(fieldName + " - Actual value not equals expected value. Expected: " + expvalue + ", Actual: " + actvalue);
            }
        }catch (Exception e)
        {
            Base.LogMessage(String.format(fieldName + " - Actual value not equals expected value. Expected: " + expvalue + ", Actual: " + actvalue));
            throw new Exception(e);
        }
    }

    //to remove characters from a string
    public static String RemoveCharactersFromDoubleString(String strValue){
        Double dbProdPrice = Double.valueOf(strValue.replaceAll("[^\\d.]+", ""));
        String value = String.valueOf(dbProdPrice);

        return value;
    }
}
