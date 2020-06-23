package com.ibm.training.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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

public class TestCaseID_RETC074 {
	private WebDriver driver;
	private String baseUrl;
	private LoginPOM loginPOM;
	private UsersPOM userPOM;
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
		Thread.sleep(1000);
		screenShot.captureScreenShot(userName);

	}
	
	@Test(dataProvider = "xls-inputs", dataProviderClass = UserDataProviders.class, dependsOnMethods = {"loginTest"})
	public void userChangeRoleTest(String user, String changeRole) throws InterruptedException {
		userPOM.mouseOverToUsersLnk();
		userPOM.clickAllUsersLnk();
		userPOM.clickUserCheckBx(user);
		screenShot.captureScreenShot(user);
		userPOM.selectChangeRoleTo(changeRole);
		screenShot.captureScreenShot(changeRole);
		userPOM.clickChangeBtn();
		userPOM.verifyMessage();
		userPOM.verifyChangedRole(user, changeRole);
		screenShot.captureScreenShot(user+"_"+changeRole);
	}
	
}