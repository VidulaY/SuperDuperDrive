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

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void testUserSignupLogin(){
		String username = "test";
		String password = "password";
		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Warren", "Buffett", username, password);

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Order(1)
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


	@Order(3)
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

	@Order(2)
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
		System.out.println("*** Clicked Notes Tab 2 *** ");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-note-button"))).click();

		HomePage homeTest = new HomePage(driver);
		Assertions.assertThrows(NoSuchElementException.class, homeTest::getNoteDescriptionDisplay);
		Assertions.assertThrows(NoSuchElementException.class, homeTest::getNoteTitleDisplay);
	}

	@Order(4)
	@Test
	public void testCredentialAddition(){
		String username= "test";
		String password = "password";
		String firstname = "Warren";
		String lastname = "Buffett";
		String credentialURL = "www.google.com";
		String credentialUsername = "test";
		String credentialPassword = "pass";


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
		driver.findElement(By.id("nav-credentials-tab")).click();

		System.out.println("*** Home page *** ");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-save-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();

		Assertions.assertEquals(credentialURL, wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url-display"))).getText());
		Assertions.assertEquals(credentialUsername, wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username-display"))).getText());
		Assertions.assertNotEquals(credentialPassword, wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password-display"))).getText());
	}

	@Order(6)
	@Test
	public void testCredentialEdit(){
		String username= "test";
		String password = "password";
		String firstname = "Warren";
		String lastname = "Buffett";
		String credentialURL = "www.google.com";
		String credentialUsername = "test";
		String credentialPassword = "pass";


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
		driver.findElement(By.id("nav-credentials-tab")).click();

		System.out.println("*** Home page *** ");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-credential-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys("updated URL");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys("updated username");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys("updated password");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-save-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();

		Assertions.assertEquals("updated URL", wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url-display"))).getText());
		Assertions.assertEquals("updated username", wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username-display"))).getText());
		Assertions.assertNotEquals("updated password", wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password-display"))).getText());
	}
	@Order(5)
	@Test
	public void testCredentialDelete(){
		String username= "test";
		String password = "password";
		String firstname = "Warren";
		String lastname = "Buffett";
		String credentialURL = "www.google.com";
		String credentialUsername = "test";
		String credentialPassword = "pass";


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
		driver.findElement(By.id("nav-credentials-tab")).click();

		System.out.println("*** Home page *** ");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential-button"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).sendKeys(credentialUsername);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).sendKeys(credentialPassword);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-save-button"))).click();

		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-credential-button"))).click();

		HomePage homeTest = new HomePage(driver);
		Assertions.assertThrows(NoSuchElementException.class, homeTest::getCredentialURLDisplay);
		Assertions.assertThrows(NoSuchElementException.class, homeTest::getCredentialUsernameDisplay);
		Assertions.assertThrows(NoSuchElementException.class, homeTest::getCredentialPasswordDisplay);
	}
}
