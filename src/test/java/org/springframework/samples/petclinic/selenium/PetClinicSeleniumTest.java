package org.springframework.samples.petclinic.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PetClinicSeleniumTest {

	WebDriver drv;
	WebElement ele;
	String txt;
	String url = "http://localhost:8088/";
	String fName = "Atakan";
	String lName = "SARAC";
	String address = "IST";
	String city = "ATASEHIR";
	String telephone = "12345";
	String pet = "Karabas";
	String pet_bday = "2021-12-20";

	@BeforeClass
	public void initializeTest() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--allow--insecure-localhost");
		opt.addArguments("acceptInsecureCerts");
		opt.addArguments("--ignore-certificate-errors");
		opt.addArguments("--disable-notifications");
		opt.addArguments("disable-infobars");
		drv = new ChromeDriver();

	}

	@AfterClass
	public void endTest() {
		drv.quit();
	}

	@Test
	public void openBrowser() {
		drv.get(url);
	}

	@Test(dependsOnMethods = { "openBrowser" })
	public void createOwner() throws InterruptedException {
		drv.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		drv.get(url + "/owners/find");
		drv.findElement(By.xpath("/html/body/div/div/a")).click();
		drv.findElement(By.id("firstName")).sendKeys(fName);
		lName = lName + Math.random();
		drv.findElement(By.id("lastName")).sendKeys(lName);
		drv.findElement(By.id("address")).sendKeys(address);
		drv.findElement(By.id("city")).sendKeys(city);
		drv.findElement(By.id("telephone")).sendKeys(telephone);
		drv.findElement(By.className("btn-default")).submit();
		Thread.sleep(2000);
	}

	@Test(dependsOnMethods = { "createOwner" })
	public void findOwner() throws InterruptedException {
		drv.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		drv.get(url + "/owners/find");
		drv.findElement(By.id("lastName")).sendKeys(lName);
		drv.findElement(By.className("btn-default")).submit();
		try {
			if (drv.findElement(By.xpath("//*[@id=\"lastNameGroup\"]/div/span/div/p")) != null) {
				drv.quit();
			}
			Thread.sleep(2000);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}

	}

	@Test(dependsOnMethods = { "findOwner" })
	public void updateOwner() throws InterruptedException {
		drv.findElement(By.xpath("/html/body/div/div/a[1]")).click();
		drv.findElement(By.id("city")).clear();
		drv.findElement(By.id("city")).sendKeys(city + " / KADIKOY");
		drv.findElement(By.className("btn-default")).submit();
		Thread.sleep(2000);

	}

	@Test(dependsOnMethods = { "updateOwner" })
	public void addPet() throws InterruptedException {
		drv.findElement(By.xpath("/html/body/div/div/a[2]")).click();
		drv.findElement(By.id("name")).sendKeys(pet);
		drv.findElement(By.cssSelector("input[placeholder='YYYY-MM-DD']")).sendKeys(pet_bday);
		Select slct = new Select(drv.findElement(By.cssSelector("select[id='type'][name='type']")));
		slct.selectByVisibleText("dog");
		Thread.sleep(2000);
		drv.findElement(By.className("btn-default")).submit();
		Thread.sleep(2000);

	}

	@Test(dependsOnMethods = { "addPet" })
	public void addVisit() throws InterruptedException {
		int j = 10;
		for (int i = 1; i <= 5; i++) {
			drv.findElement(By.xpath("/html/body/div/div/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/a")).click();
			drv.findElement(By.name("date")).clear();
			String date = "2021-11-";
			drv.findElement(By.name("date")).sendKeys(date + j);
			j += 3;
			drv.findElement(By.name("description")).sendKeys(pet + "'s Visit No : " + i);
			drv.findElement(By.className("btn-default")).submit();
			Thread.sleep(5000);
		}
	}

}
