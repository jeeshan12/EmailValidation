package email.automation.testcases;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.mashape.unirest.http.exceptions.UnirestException;

import email.automation.basepage.BasePage;
import email.automation.emailservice.EmailService;
import email.automation.pages.IsMyEmailWorkingPage;
import email.automation.testbase.TestBase;
import email.automation.utils.SeleniumUtil;

public class EmailValidationTest extends TestBase {

	private BasePage basePage;

	private IsMyEmailWorkingPage emailPage;

	private EmailService mailService;

	@BeforeClass
	public void setUp() {
		basePage = new BasePage(getDriver());

		basePage.navigateToUrl(System.getProperty("applicationUrl"));
	}

	@Test(description = "This test will verify the subject and body for a received email", dataProvider = "emaildata")
	public void testEmailValidation(Map<String, String> map)
			throws JsonParseException, JsonMappingException, IOException, UnirestException {

		emailPage = new IsMyEmailWorkingPage(getDriver());

		mailService = new EmailService();

		emailPage.enterTextIntoEmailTextBox(mailService.getEmailId());

		emailPage.clickCheckButton();

		Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

		String emailContent = mailService.getMessageWithSubject(map.get("subject")).getText();
		System.out.println(emailContent);

		Assert.assertTrue(org.apache.commons.lang3.StringUtils.isNotEmpty(emailContent));

		mailService.reset();

	}

	@DataProvider(name = "emaildata")
	public Object[][] getDataFromJson() {
		return SeleniumUtil.getDataFromJSON(SystemUtils.getUserDir() + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "data" + File.separator + "subject.json", "email");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		getDriver().quit();
	}

}
