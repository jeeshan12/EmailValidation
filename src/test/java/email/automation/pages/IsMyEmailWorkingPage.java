package email.automation.pages;

import static email.automation.pageconstants.IsMyEmailWorkingPageConstants.CHECK_BUTTON;
import static email.automation.pageconstants.IsMyEmailWorkingPageConstants.EMAIL_TEXTBOX;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import email.automation.utils.SeleniumUtil;

/**
 * Class for IsMyEmailWorkingPage
 * @author Jeeshan
 *
 */
public class IsMyEmailWorkingPage {

	protected WebDriver driver;

	@FindBy(id = EMAIL_TEXTBOX)
	private WebElement txtEmail;

	@FindBy(id = CHECK_BUTTON)
	private WebElement btnCheck;

	

	public IsMyEmailWorkingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	
	public void enterTextIntoEmailTextBox(final String email) {
		SeleniumUtil.enterDataIntoTextBox(txtEmail, email);
	}
	
	public void clickCheckButton() {
		SeleniumUtil.clickElement(btnCheck);
	}
}
