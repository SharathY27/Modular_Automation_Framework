package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalVariables {

	public static String browserName = "";
	public static String url = "";

	public static void loadProperties() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream("Config/config.properties")) {
			if (inputStream == null) {
				throw new IOException("Unable to load 'Config/config.properties' from src/test/resources");
			}

			Properties properties = new Properties();
			properties.load(inputStream);

			browserName = properties.getProperty("browser");
			url = properties.getProperty("url");

			System.out.println("Browser Name: " + browserName);
			System.out.println("URL: " + url);

		} catch (IOException e) {
			System.err.println("Error loading configuration: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
