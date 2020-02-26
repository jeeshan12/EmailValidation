package email.automation.browserfactory;

import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * Class to handle web driver. Always return a new session of {@link WebDriver}
 * 
 */
public class BrowserFactory {

	private static final Function<String, WebDriver> driverSelect = (browser) -> {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver();
		} else {
			return null;
		}
	};

	public static final WebDriver getDriver(final String browserName) {
		return driverSelect.apply(browserName);
	}
}