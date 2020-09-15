package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
//
//	@Test
//	public void getLoginPage() {
//		driver.get("http://localhost:" + this.port + "/login");
//		Assertions.assertEquals("Login", driver.getTitle());
//	}
//
//	@Test
//	public void getSignupPage() {
//		driver.get("http://localhost:" + this.port + "/signup");
//		Assertions.assertEquals("Sign Up", driver.getTitle());
//	}
//
//	@Test
//	public void testUnauthorizedAccess() {
//		driver.get("http://localhost:" + this.port + "/home");
//		Assertions.assertNotEquals("Home", driver.getTitle());
//		Assertions.assertEquals("Login", driver.getTitle());
//	}
//	@Test
//	public void testUserSignupLogin(){
//		String username = "test";
//		String password = "password";
//		driver.get("http://localhost:" + this.port + "/signup");
//
//		SignupPage signupPage = new SignupPage(driver);
//		signupPage.signup("Warren", "Buffett", username, password);
//
//		driver.get("http://localhost:" + this.port + "/login");
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.login(username, password);
//		Assertions.assertEquals("Home", driver.getTitle());
//	}

	@Order(2)
	@Test
	public void testNoteAddition(){
		String username= "test";
		String password = "password";
		String firstname = "Warren";
		String lastname = "Buffett";
		String noteTitle = "Note1";
		String noteDescription = "Dsecription for note1";

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		System.out.println("*** Logged In *** ");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait1 = new WebDriverWait(driver, 100);
		driver.findElement(By.id("nav-notes-tab")).click();

		System.out.println("*** Home page *** ");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-save-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertEquals(noteTitle, wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title-display"))).getText());
		Assertions.assertEquals(noteDescription, wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description-display"))).getText());
	}


	@Test
	public void testNoteEdit(){
		String username= "test";
		String password = "password";
		String firstname = "Warren";
		String lastname = "Buffett";
		String noteTitle = "Note1";
		String noteDescription = "Dsecription for note1";

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		System.out.println("*** Logged In *** ");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait1 = new WebDriverWait(driver, 100);
		driver.findElement(By.id("nav-notes-tab")).click();

		System.out.println("*** Home page *** ");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-save-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		WebDriverWait delay = new WebDriverWait(driver, 30);
		System.out.println("*** Clicked Notes Tab 2 *** ");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-note-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("Updated Note1");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys("Updated Description for Note1");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-save-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();

		Assertions.assertEquals("Updated Note1", wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title-display"))).getText());
		Assertions.assertEquals("Updated Description for Note1", wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description-display"))).getText());
	}

	@Order(1)
	@Test
	public void testNoteDelete(){
		String username= "test";
		String password = "password";
		String firstname = "Warren";
		String lastname = "Buffett";
		String noteTitle = "Note1";
		String noteDescription = "Dsecription for note1";

		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		System.out.println("*** Logged In *** ");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait1 = new WebDriverWait(driver, 100);
		driver.findElement(By.id("nav-notes-tab")).click();

		System.out.println("*** Home page *** ");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(noteDescription);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-save-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		WebDriverWait delay = new WebDriverWait(driver, 30);
		System.out.println("*** Clicked Notes Tab 2 *** ");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-note-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();

		HomePage homeTest = new HomePage(driver);
		Assertions.assertThrows(NoSuchElementException.class, homeTest::getNoteDescriptionDisplay);
		Assertions.assertThrows(NoSuchElementException.class, homeTest::getNoteTitleDisplay);

	}
}
