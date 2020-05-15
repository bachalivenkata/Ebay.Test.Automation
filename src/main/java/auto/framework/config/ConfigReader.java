package auto.framework.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    public static Properties config = new Properties();

    public ConfigReader() {
    }

    public static void LoadConfig(String dir) throws Exception {
        InputStream inputStream = new FileInputStream(dir);
        config.load(inputStream);
    }

    public static String GetGlobalProperty(String key) throws Exception {
        String dir = "src/test/java/resources/Environment.properties";

        //To load the Environment properties.
        LoadConfig(dir);

        //Getting property values using Key
        String propertyValue = config.getProperty(key);
        return propertyValue;
    }

    public static String GetUIElementsProperty(String key) throws Exception {
        String dir = System.getProperty("user.dir") + "\\src\\test\\java\\objectrepository\\OR.properties";

        //To load the Object Repository properties.
        LoadConfig(dir);

        //Getting property values using Key
        String propertyValue = config.getProperty(key);
        return propertyValue;
    }
}
