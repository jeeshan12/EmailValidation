package email.automation.testbase;

import org.openqa.selenium.WebDriver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeTest;

import email.automation.browserfactory.BrowserFactory;

public class TestBase {

	private static final String ENVIRONMENT_CONTEXT = "envContext.xml";

	private WebDriver driver;

	@SuppressWarnings("resource")
	@BeforeTest(alwaysRun = true)
	public void setTestSetUp() {

		new ClassPathXmlApplicationContext(ENVIRONMENT_CONTEXT);
		setDriver(BrowserFactory.getDriver(System.getProperty("browserName")));
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
