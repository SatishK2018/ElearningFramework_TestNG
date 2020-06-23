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

public class UsersPOM {
	private WebDriver driver; 
	private WebElement table;
	private List<WebElement> rows;
	private Actions act;
	
	public UsersPOM(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//table[@class='wp-list-table widefat fixed striped users']")
	private WebElement userTable; 
	
	@FindBy(id="new_role")
	private WebElement changeRoleLstBx; 
	
	@FindBy(id="changeit")
	private WebElement changeBtn; 
	
	@FindBy(xpath="//p[text()='Changed roles.']")
	private WebElement messge;
	
	
	public void mouseOverToUsersLnk() {
		//this.usersLnk.click();	
		act = new Actions(driver);
		act.moveToElement(driver.findElement(By.partialLinkText("Users"))).perform();
	}
	
	public void clickAllUsersLnk() {
		driver.findElement(By.linkText("All Users")).click();	
	}
	
	public void clickAddNewLnk() {
		driver.findElement(By.linkText("Add New")).click();	
	}
	
	public void clickUserCheckBx(String user) {
		table = this.userTable.findElement(By.tagName("tbody"));
		rows = table.findElements(By.tagName("tr"));
		
		for(int i=0; i<rows.size();i++) {
			//System.out.println(rows.get(i).getText());
			String names = rows.get(i).findElements(By.tagName("td")).get(1).getText();
			//System.out.println(names);
			if(names.equalsIgnoreCase(user))
				rows.get(i).findElement(By.tagName("th")).findElement(By.name("users[]")).click();
		
		}
	}
	
	public void selectChangeRoleTo(String changeRole) {
		Select changeRoleList = new Select(this.changeRoleLstBx);
		changeRoleList.selectByVisibleText(changeRole);	
	}
	
	public void clickChangeBtn() {
		this.changeBtn.click();
	}
	
	public void verifyMessage() {
		String actual = this.messge.getText();
		String expected = "Changed roles.";
		
		Assert.assertEquals(actual, expected);
				
	}
	
	public void verifyChangedRole(String user, String changeRole) {
			table = this.userTable.findElement(By.tagName("tbody"));
			rows = table.findElements(By.tagName("tr"));
			
			for(int i=0; i<rows.size();i++) {
				String names = rows.get(i).findElements(By.tagName("td")).get(1).getText();
				if(names.equalsIgnoreCase(user)) {
					String actual = rows.get(i).findElements(By.tagName("td")).get(3).getText();
					Assert.assertEquals(actual, changeRole);
			}
		}
	}
	
	public void verifyUserDetails(String expected_username, String expected_email, String expected_role) {
		table = this.userTable.findElement(By.tagName("tbody"));
		rows = table.findElements(By.tagName("tr"));
		
		for(int i=0; i<rows.size();i++) {
			String actual_username = rows.get(i).findElements(By.tagName("td")).get(0).getText();
			String actual_email = rows.get(i).findElements(By.tagName("td")).get(2).getText();
			String actual_role = rows.get(i).findElements(By.tagName("td")).get(3).getText();
			if(actual_username.equals(expected_username)) {
				Assert.assertEquals(actual_email, expected_email);
				Assert.assertEquals(actual_role, expected_role);
		}
	}
}
}
