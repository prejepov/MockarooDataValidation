package com.mockaroo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {

	WebDriver driver;
	private static final String FILENAME = "C:\\Users\\Rejepov\\Desktop\\MOCK_DATA.CSV";

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		// step 2
		driver.get("https://mockaroo.com/");
	}

	@Test
	public void test() throws InterruptedException {
		// step3
		String expectedTitle = "Mockaroo - Random Data" + " Generator and API Mocking Tool | JSON / CSV / SQL / Excel"; // one
																														// space
																														// removed
																														// after
																														// Mockaroo
		String actualTitle = driver.getTitle(); // the title do not match
		assertEquals(expectedTitle, actualTitle);

		// step4
		String logo = driver.findElement(By.xpath("//div[@class='brand']")).getText(); // //div[@class='brand'] =
																						// mockaroo
		String logoText = driver.findElement(By.xpath("//div[@class='tagline']")).getText();
		assertEquals(logo, "mockaroo"); // test fails with Mockaroo, changed to mockaroo
		assertEquals(logoText, "realistic data generator");

		// step5

		List<WebElement> x = driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		for (WebElement webElement : x) {
			webElement.click();

		}
		// step6
		String fieldName = driver.findElement(By.xpath("//div[@class='column column-header column-name']")).getText();
		String type = driver.findElement(By.xpath("//div[@class='column column-header column-type']")).getText();
		String options = driver.findElement(By.xpath("//div[@class='column column-header column-options']")).getText();

		assertEquals(fieldName, "Field Name");
		assertEquals(type, "Type");
		assertEquals(options, "Options");

		// step7
		boolean button = driver
				.findElement(By.xpath("//a[@class='btn btn-default " + "add-column-btn add_nested_fields']"))
				.isEnabled();
		assertTrue(button);

		// step8
		String numOfRows = driver.findElement(By.id("num_rows")).getAttribute("value");
		assertEquals(numOfRows, "1000");

		// step9
		Select format = new Select(driver.findElement(By.id("schema_file_format")));
		assertEquals(format.getFirstSelectedOption().getText(), "CSV");

		// step10
		Select lineEnding = new Select(driver.findElement(By.id("schema_line_ending")));
		assertEquals(lineEnding.getFirstSelectedOption().getText(), "Unix (LF)");

		// step11
		boolean header = driver.findElement(By.id("schema_include_header")).isSelected();
		assertTrue(header);

		boolean bom = driver.findElement(By.id("schema_bom")).isSelected();
		assertFalse(bom);

		// step12
		WebElement addButton = driver.findElement(By.linkText("Add another field"));
		addButton.click();

		WebElement fName = driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[7]"));
		fName.sendKeys("City");

		// step13
		driver.findElement(By.xpath("(//input[@class='btn btn-default'])[7]")).click();
		Thread.sleep(1000);
		String dialogBox = driver.findElement(By.xpath("(//h3[@class='modal-title'])[1]")).getText();
		assertEquals("Choose a Type", dialogBox);

		// step14
		WebElement searchField = driver.findElement(By.id("type_search_field"));
		searchField.sendKeys("City");

		WebElement searchClick = driver.findElement(By.xpath("//div[@class='type-name']"));
		searchClick.click();

		// step15
		Thread.sleep(1000);
		addButton.click();
		fName.clear();
		fName.sendKeys("Country");

		driver.findElement(By.xpath("(//input[@class='btn btn-default'])[7]")).click();
		Thread.sleep(1000);
		String dialogBox1 = driver.findElement(By.xpath("(//h3[@class='modal-title'])[1]")).getText();
		assertEquals("Choose a Type", dialogBox1);

		Thread.sleep(1000);
		searchField.sendKeys("Country");
		searchClick.click();

		// //step16
		// Thread.sleep(1000);
		// driver.findElement(By.id("download")).click();

		// step17
		ArrayList<String> Cities = new ArrayList<>();
		ArrayList<String> Country = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String strCurrentLine;

			while ((strCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				String[] str = strCurrentLine.split(",");
				Country.add(str[0]); // step20
				Cities.add(str[1]); // step21

			}
			// System.out.println(Country);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// step18
		assertEquals(Cities.get(0), "City");
		assertEquals(Country.get(0), "Country");

		// step19
		int recordCount = 0;
		for (int i = 1; i < Cities.size(); i++) {
			recordCount++;
		}
		assertEquals(recordCount, 1000);

		// step22
		Collections.sort(Cities);
		String longestName = Cities.get(0);
		String shortetstName = Cities.get(0);

		for (int i = 0; i < Cities.size(); i++) {
			if (longestName.length() < Cities.get(i).length()) {
				 longestName = Cities.get(i);
			} 
			if (shortetstName.length() > Cities.get(i).length()) {
				shortetstName = Cities.get(i);
			} 
		} 
		System.out.println("Longest Name: "+longestName);
		System.out.println("Shortes Name: " +shortetstName);

		
		//step23
		Collections.sort(Country);
		//System.out.println(Country);
		int countryCounter = 1;
		int uniqueCountry = 1;
		
		for (int i = 0; i < Country.size()-1; i++) {
			if(Country.get(i).equals(Country.get(i+1))){
				System.out.println(Country.get(i));
				countryCounter++;
				continue;
			}
			System.out.println(Country.get(i) + "-" + countryCounter);
			countryCounter = 1;
			uniqueCountry++;
		}
		//step24
		Set<String> citiesSet = new HashSet<>(Cities);
		//System.out.println(citiesSet);
		
		//step25
		int cityCounter = 1;
		int uniqueCity = 1;
		
		for (int i = 0; i < Cities.size()-1; i++) {
			if(Cities.get(i).equals(Cities.get(i+1))){
				//System.out.println(Cities.get(i));
				cityCounter++;
				continue;
			}
			//System.out.println(Cities.get(i) + "-" + cityCounter);
			cityCounter = 1;
			uniqueCity++;
	}
		assertEquals(uniqueCity, citiesSet.size());
		
		//step26
		Set<String> countrySet = new HashSet<>(Country);
		
		//step27
		assertEquals(uniqueCountry, countrySet.size());
		
		
	}
	
}
