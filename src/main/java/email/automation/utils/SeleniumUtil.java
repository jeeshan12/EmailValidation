package email.automation.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * Class to handle common UI operation
 *
 */
public final class SeleniumUtil {

	/**
	 * method to click {@link WebElement}
	 * 
	 * @param elementToClick
	 */
	public static void clickElement(final WebElement elementToClick) {
		elementToClick.click();
	}

	/**
	 * Method to enter the data in text box
	 * 
	 * @param element      is {@link WebElement}
	 * @param valueToEnter is the value to enter in textbox
	 */
	public static void enterDataIntoTextBox(final WebElement element, final String valueToEnter) {
		element.sendKeys(valueToEnter);
	}

	
	/**
	 * Method to read the json
	 * 
	 * @param jsonPath is file path 
	 * @param jsonArrayName is json array name
	 * @return {@link Object}
	 */
	@SuppressWarnings("deprecation")
	public static Object[][] getDataFromJSON(final String jsonPath, final String jsonArrayName) {
		Map<String, String> datamap;
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = null;
		Object[][] data = null;
		if (jsonArrayName != null) {
			try {
				jsonObject = jsonParser.parse(new FileReader(new File(jsonPath))).getAsJsonObject();
				if (jsonObject != null) {
					JsonArray jsonArray = (JsonArray) jsonObject.get(jsonArrayName);
					data = new Object[jsonArray.size()][1];
					int rowNumber = 0;
					for (JsonElement jsonElemet : jsonArray) {
						datamap = new HashMap<>();
						for (Map.Entry<String, JsonElement> entry : jsonElemet.getAsJsonObject().entrySet()) {
							datamap.put(entry.getKey(), entry.getValue().toString().replaceAll("\"", ""));

						}
						data[rowNumber][0] = datamap;
						rowNumber++;
					}

				}
			} catch (FileNotFoundException e) {
				System.out.println("File Not found at the location :" + jsonPath + " Exception is :" + e);

			}
		}
		return data;

	}
}
