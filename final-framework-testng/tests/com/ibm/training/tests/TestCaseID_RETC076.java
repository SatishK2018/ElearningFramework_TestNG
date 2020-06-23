package com.ibm.training.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.training.bean.LoginBean;
import com.training.dao.ELearningDAO;
import com.training.dataproviders.LoginDataProviders;
import com.training.dataproviders.NewUserDataProviders;
import com.training.dataproviders.UserDataProviders;
import com.training.generics.ScreenShot;
import com.training.pom.AddNewUserPOM;
import com.training.pom.LoginPOM;
import com.training.pom.UsersPOM;
import com.training.readexcel.ReadExcel;
import com.training.utility.DriverFactory;
import com.training.utility.DriverNames;

public class TestCaseID_RETC076 {
	private WebDriver driver;
	private static final PrintStream PrintStream = null;
	private String baseUrl;
	private LoginPOM loginPOM;
	private UsersPOM userPOM;
	private AddNewUserPOM newUserPOM;
	private static Properties properties;
	private ScreenShot screenShot;

	@BeforeTest
	public static void setUpBeforeClass() throws IOException {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
	}

	@BeforeClass
	public void setUp() throws Exception {
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver);
		userPOM = new UsersPOM(driver);
		newUserPOM = new AddNewUserPOM(driver);
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		// open the browser
		driver.get(baseUrl);
	}

	@AfterClass
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test(dataProvider = "xls-inputs", dataProviderClass = LoginDataProviders.class)
	public void loginTest(String userName, String password) throws InterruptedException {
		loginPOM.sendUserName(userName);
		loginPOM.sendPassword(password);
		loginPOM.clickLoginBtn();
		screenShot.captureScreenShot(userName);
	}
	
	@Test(dataProvider = "xls-inputs", dataProviderClass = NewUserDataProviders.class,dependsOnMethods = {"loginTest"} )
	public void addNewUserTest(String username, String email, String firstName, String lastName, String webSite, String password, String role) throws InterruptedException {
		userPOM.mouseOverToUsersLnk();
		userPOM.clickAddNewLnk();
		newUserPOM.sendUsername(username);
		newUserPOM.sendEmail(email);
		newUserPOM.sendFirstName(firstName);
		newUserPOM.sendLastName(lastName);
		newUserPOM.sendWebSite(webSite);
		screenShot.captureScreenShot(username);
		newUserPOM.clickShowPasswordBtn();
		newUserPOM.sendPassword(password);
		newUserPOM.confirmPassChkBxIsDisplayed();
		newUserPOM.verifyRoleLstBx();
		newUserPOM.selectRole(role);
		screenShot.captureScreenShot(username+"_"+role);
		newUserPOM.clickAddNewUserBtn();
		newUserPOM.verifyEditUserLnk();
		screenShot.captureScreenShot(firstName+"_"+role);
		userPOM.verifyUserDetails(username, email, role);
	}

}