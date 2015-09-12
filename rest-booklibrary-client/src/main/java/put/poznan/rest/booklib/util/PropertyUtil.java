package put.poznan.rest.booklib.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	
	private static final String PROPERTY_FILENAME = "common.properties";
	
	private static PropertyUtil instance = null;
	
	private Properties prop = new Properties();
	
	private PropertyUtil() {
		InputStream input = null;
    	try {
			input = PropertyUtil.class.getClassLoader().getResourceAsStream(PROPERTY_FILENAME);
			if (input == null) {
				throw new Exception("Unable to find " + PROPERTY_FILENAME);
			}
			//load a properties file from class path, inside static method
			prop.load(input);
    	} catch (Exception e) {
    		e.printStackTrace();
        } finally{
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        }
	}
		
	public static String getProperty(String name) {
		if (instance == null) {
			instance = new PropertyUtil();
		}
		return instance.prop.getProperty(name);
	}

}
