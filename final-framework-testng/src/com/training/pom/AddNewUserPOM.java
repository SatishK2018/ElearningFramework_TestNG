package com.training.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class AddNewUserPOM {
	private WebDriver driver; 
	private List<WebElement> value;
			
	
	public AddNewUserPOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="user_login")
	private WebElement username; 
	
	@FindBy(id="email")
	private WebElement email; 
	
	@FindBy(id="first_name")
	private WebElement firstName; 
	
	@FindBy(id="last_name")
	private WebElement lastName;
	
	@FindBy(id="url")
	private WebElement website;
		
	@FindBy(xpath="//button[@type='button'][text()='Show password']")
	private WebElement showPassBtn;
	
	@FindBy(id="pass1-text")
	private WebElement password;
	
	@FindBy(id="role")
	private WebElement roleLstBx;
	
	@FindBy(id="createusersub")
	private WebElement addNewUserBtn;
	
	@FindBy(xpath="//*[@id='message']/p/text()")
	private WebElement message;
	
	@FindBy(xpath="//*[@id='message']/p/a")
	private WebElement editUsrLnk;
	
	@FindBy(xpath="(//INPUT[@type='checkbox'])")
	private WebElement confirmPassChkBx;
	
	public void sendUsername(String username) {
		this.username.sendKeys(username);	
	}
	
	public void sendFirstName(String firstName) {
		this.firstName.sendKeys(firstName);
	}
	
	public void sendLastName(String lastName) {
		this.lastName.sendKeys(lastName);
	}
	
	public void sendEmail(String email) {
		this.email.sendKeys(email);
	}
	
	public void sendWebSite(String webSite) {
		this.website.sendKeys(webSite);
	}
	
	public void sendPassword(String password) {
		this.password.clear();
		this.password.sendKeys(password);
	}
	
	public void clickShowPasswordBtn() {
		this.showPassBtn.click();
	}
	
	public void clickAddNewUserBtn() {
		this.addNewUserBtn.click();
	}
	
	public void verifyEditUserLnk() {
		String actual = this.editUsrLnk.getText();
		String expected = "Edit user";
		
		Assert.assertEquals(actual, expected);
	}
	
	public void selectRole(String role) {
		new Select(this.roleLstBx).selectByVisibleText(role);
	}
	
	public void verifyRoleLstBx() {
		Select roleListBx = new Select(this.roleLstBx);
		value = roleListBx.getOptions();
		
		for(int i=0; i<value.size();i++) {
			System.out.println(value.get(i).getText());
		}
	}
	
	public void confirmPassChkBxIsDisplayed() {
		if(this.confirmPassChkBx.isDisplayed())
			this.confirmPassChkBx.click();
		}
	
}
