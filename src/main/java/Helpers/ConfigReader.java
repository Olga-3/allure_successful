package Helpers;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.startsWith;

public class ConfigReader {
    //не знаю, что за проблема с зависимостями, но Logger тут мне подчеркивается красным
    // и import org.apache.log4j.Logger;   тоже подчеркивается красным

    //private final Logger logger = Logger.getLogger(ConfigReader.class);

    private static final String DEBUG_PROPERTIES = "config.properties";

    //fromResource - имя ресурса, откуда будут считываться переменные

    public static String getStringSystemProperty(String name) throws IOException{
        String variable = System.getProperty(name);
        if(variable != null) {
            return variable.trim();
        }
        variable = loadProperty(name);
        if (variable == null) throw new IOException("The variable " + name + " is not set");
        return variable;
    }

    private static String loadProperty(String name, String fromResource) throws IOException {
        Properties props = new Properties();
        try {
            if(!fromResource.startsWith("/")) fromResource = "/" + fromResource;
            props.load(ConfigReader.class.getResourceAsStream(fromResource));
        } catch (IOException e) {
            throw new IOException("Error when getting the value of " + name + " from config file '" + fromResource + "'.", e);
        }
        String value = props.getProperty(name);
        if (value == null) {
            return null;
        }
        if (value.trim().equalsIgnoreCase("${" + name + "}")) {
            return null;
        }
        return value;
    }

    /*
    ниже я создала еще один такой же метод, только с одним аргументом, потому что иначе
    строка variable = loadProperty(name); подчеркивалась красным из-за количества аргументов
     */
    private static String loadProperty(String name) throws IOException {
        Properties props = new Properties();
//        try {
//            if(!fromResource.startsWith("/")) fromResource = "/" + fromResource;
//            props.load(ConfigReader.class.getResourceAsStream(fromResource);
//        } catch (IOException e) {
//            throw new IOException("Error when getting the value of " + name + " from config file '" + fromResource + "'.", e);
//        }
        String value = props.getProperty(name);
        if (value == null) {
            return null;
        }
        if (value.trim().equalsIgnoreCase("${" + name + "}")) {
            return null;
        }
        return value;
    }

}
