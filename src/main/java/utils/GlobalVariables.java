package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalVariables {

	public static String browserName = "";
	public static String url = "";

	public static void loadProperties() {
		try (InputStream inputStream = ClassLoader.getSystemClassLoader()
				.getResourceAsStream("Config/config.properties")) {
			if (inputStream == null) {
				throw new FileNotFoundException("config.properties not found in resources/Config folder");
			}
			Properties properties = new Properties();
			properties.load(inputStream);

			browserName = properties.getProperty("browser");
			url = properties.getProperty("url");
			System.out.println("Browser Name : " + browserName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
