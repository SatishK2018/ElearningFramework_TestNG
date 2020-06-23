package com.ibm.training.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

public class TestSuite {
	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private UsersPOM userPOM;
	private AddNewUserPOM newUserPOM;
	private static Properties properties;
	private ScreenShot screenShot;

	
	@BeforeTest
	public void setUp() throws Exception {
		properties = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/others.properties");
		properties.load(inStream);
		driver = DriverFactory.getDriver(DriverNames.CHROME);
		loginPOM = new LoginPOM(driver);
		userPOM = new UsersPOM(driver);
		newUserPOM = new AddNewUserPOM(driver);
		baseUrl = properties.getProperty("baseURL");
		screenShot = new ScreenShot(driver);
		// open the browser
		driver.get(baseUrl);
	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(1000);
		driver.quit();
	}

	@Test(dataProvider = "xls-inputs",priority=1, dataProviderClass = LoginDataProviders.class)
	public void loginTest(String userName, String password) throws InterruptedException {
		loginPOM.sendUserName(userName);
		loginPOM.sendPassword(password);
		loginPOM.clickLoginBtn();
		Thread.sleep(1000);
		screenShot.captureScreenShot(userName+"Login");

	}
	
	@Test(dataProvider = "xls-inputs", priority = 20, dataProviderClass = UserDataProviders.class)
	public void userChangeRoleTest(String user, String changeRole) throws InterruptedException {
		userPOM.mouseOverToUsersLnk();
		userPOM.clickAllUsersLnk();
		userPOM.clickUserCheckBx(user);
		screenShot.captureScreenShot("Select_"+user);
		userPOM.selectChangeRoleTo(changeRole);
		userPOM.clickChangeBtn();
		userPOM.verifyMessage();
		userPOM.verifyChangedRole(user, changeRole);
		screenShot.captureScreenShot(user+"_changed to_"+changeRole);
	}
	
	@Test(dataProvider = "xls-inputs", priority = 10,dataProviderClass = NewUserDataProviders.class)
	public void addNewUserTest(String username, String email, String firstName, String lastName, String webSite, String password, String role) throws InterruptedException {
		userPOM.mouseOverToUsersLnk();
		userPOM.clickAddNewLnk();
		newUserPOM.sendUsername(username);
		newUserPOM.sendEmail(email);
		newUserPOM.sendFirstName(firstName);
		newUserPOM.sendLastName(lastName);
		newUserPOM.sendWebSite(webSite);
		screenShot.captureScreenShot("Add new user_"+username);
		newUserPOM.clickShowPasswordBtn();
		newUserPOM.sendPassword(password);
		newUserPOM.confirmPassChkBxIsDisplayed();
		newUserPOM.verifyRoleLstBx();
		newUserPOM.selectRole(role);
		newUserPOM.clickAddNewUserBtn();
		newUserPOM.verifyEditUserLnk();
		screenShot.captureScreenShot(firstName+"_"+role+"Added");
		userPOM.verifyUserDetails(username, email, role);
	}
	
}