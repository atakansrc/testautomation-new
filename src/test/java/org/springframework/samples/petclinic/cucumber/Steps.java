package org.springframework.samples.petclinic.cucumber;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Steps {

	WebDriver drv;
	WebElement ele;
	String txt;
	String url = "http://localhost:8088/";
	String fName = "Atakan";
	String lName = "SOYAD_DENEME_5";
	String address = "IST";
	String city = "ATASEHIR";
	String telephone = "12345";
	String pet = "Karabas";
	String pet_bday = "2021-12-20";

	@Before
	public void launch_chrome() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--allow--insecure-localhost");
		opt.addArguments("acceptInsecureCerts");
		opt.addArguments("--ignore-certificate-errors");
		opt.addArguments("--disable-notifications");
		opt.addArguments("disable-infobars");
		opt.addArguments("--headless");
		drv = new ChromeDriver(opt);
	}

	@After(order = 0)
	public void close_chrome() {
		drv.quit();
	}

	@When("Open LocalHost")
	public void open_local_host() {
		drv.get(url);
	}

	@Then("Click Find Owners")
	public void click_find_owners() {
		drv.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		drv.get(url + "/owners/find");
	}

	@Then("Navigate Add Owner")
	public void navigate_add_owner() {
		drv.findElement(By.xpath("/html/body/div/div/a")).click();
	}

	@Then("Fill the blanks")
	public void fill_the_blanks() {
		drv.findElement(By.id("firstName")).sendKeys(fName);
		drv.findElement(By.id("lastName")).sendKeys(lName);
		drv.findElement(By.id("address")).sendKeys(address);
		drv.findElement(By.id("city")).sendKeys(city);
		drv.findElement(By.id("telephone")).sendKeys(telephone);
	}

	@Then("Click Add Owner")
	public void click_add_owner() {
		drv.findElement(By.className("btn-default")).submit();
	}

	@Then("User successfully created")
	public void user_successfully_created() {
		System.out.println("User : " + fName + " " + lName + "created successfully.");
	}

	@When("User needs to be find")
	public void user_needs_to_be_find() {
		System.out.println("Starting Find User Process...");
	}

	@Then("Type Last name of User")
	public void type_last_name_of_user() throws InterruptedException {
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
		
		
		try {
			if (drv.findElement(By.xpath("//*[@id=\"owners\"]")) != null) {
				drv.findElement(By.xpath("//*[@id=\"owners\"]/tbody/tr[position()=last()]/td[1]/a")).click();				
				System.err.println("LAST added user selected...");
			}
		} catch (NoSuchElementException e) {
			System.err.println("User DOES NOT have similarity with other account...");
		}
		
		
	}

	@When("User needs to be updated")
	public void user_needs_to_be_updated() {
		System.out.println("Starting Update User Process...");
	}

	@Then("Click Edit Owner")
	public void click_edit_owner() {
		drv.findElement(By.xpath("/html/body/div/div/a[1]")).click();
	}

	@Then("Update the Blanks")
	public void update_the_blanks() {
		drv.findElement(By.id("city")).clear();
		drv.findElement(By.id("city")).sendKeys(city + " / KADIKOY");
	}

	@Then("Click Update Owner")
	public void click_update_owner() {
		drv.findElement(By.className("btn-default")).submit();
	}

	@When("User buys pet")
	public void user_buys_pet() throws InterruptedException {
		click_find_owners();
		type_last_name_of_user();
	}

	@Then("Click Add New Pet")
	public void click_add_new_pet() {
		drv.findElement(By.xpath("/html/body/div/div/a[2]")).click();
	}

	@Then("Fill Pets attributes")
	public void fill_pets_attributes() throws InterruptedException {
		try {
			if(drv.findElement(By.className("help-inline")) != null) {
				drv.findElement(By.id("name")).sendKeys(pet + "bird");
				drv.findElement(By.cssSelector("input[placeholder='YYYY-MM-DD']")).sendKeys(pet_bday);
				Select slct = new Select(drv.findElement(By.cssSelector("select[id='type'][name='type']")));
				slct.selectByVisibleText("bird");
			}
		} catch (NoSuchElementException e) {
			drv.findElement(By.id("name")).sendKeys(pet);
			drv.findElement(By.cssSelector("input[placeholder='YYYY-MM-DD']")).sendKeys(pet_bday);
			Select slct = new Select(drv.findElement(By.cssSelector("select[id='type'][name='type']")));
			slct.selectByVisibleText("dog");
			Thread.sleep(2000);
		}
		
	}

	@Then("Click Add Pet")
	public void click_add_pet() {
		drv.findElement(By.className("btn-default")).submit();
	}

	@When("Pet need visits")
	public void pet_need_visits() throws InterruptedException {
		user_buys_pet();
	}

	@Then("Click Add Visit")
	public void click_add_visit() {
		//
	}

	@Then("Fill the Visit attributes")
	public void fill_the_visit_attributes() throws InterruptedException {
		int j = 10;
		for (int i = 1; i <= 5; i++) {
			drv.findElement(By.xpath("/html/body/div/div/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/a")).click();
			drv.findElement(By.name("date")).clear();
			String date = "2021-11-";
			drv.findElement(By.name("date")).sendKeys(date + j);
			j += 3;
			drv.findElement(By.name("description")).sendKeys(pet + "'s Visit No : " + i);
			drv.findElement(By.className("btn-default")).submit();
			Thread.sleep(2000);
		}

	}

}
