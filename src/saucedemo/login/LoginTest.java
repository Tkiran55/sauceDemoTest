package saucedemo.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import saucedemo.Setup;

public class LoginTest extends Setup {
	
	public WebElement getLogo() {
        return driver.findElement(By.className("login_logo"));
    }
    
    public WebElement getUsername() {
        return driver.findElement(By.id("user-name"));
    }
    
    public WebElement getPassword() {
        return driver.findElement(By.id("password"));
    }
    
    public WebElement getLoginButton() {
        return driver.findElement(By.id("login-button"));
    }
    
    public void performLogin(String usernameText, String passwordText) {    
        WebElement username = getUsername();
        WebElement password = getPassword();
        WebElement loginbutton = getLoginButton();
        
        username.clear();
        password.clear();
        
        username.sendKeys(usernameText);
        password.sendKeys(passwordText);
        loginbutton.click();
        System.out.println("Login Attempted with Username: " + usernameText);
    }

    
    @Test(priority = 3)
    public void logInTest() {
        performLogin("standard_user", "secret_sauce");
    }

}
