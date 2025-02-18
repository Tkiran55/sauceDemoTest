package saucedemo.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import saucedemo.Setup;

public class LoginPageTest extends Setup {
    
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
    
    

    @Test(priority = 3)
    public void performIsDisplayed() {
        WebElement logo = getLogo();
        System.out.println("Swag Labs Logo is displayed: " + logo.isDisplayed());
        if (logo.isDisplayed()) {
            System.out.println("Landed on the login page");
        } else {
            System.out.println("Not on the login page");
        }
    }
    
    @Test(priority = 4)
    public void performIsSelected() {
        WebElement username = getUsername();
        WebElement password = getPassword();
        
        System.out.println("Username field is selected: " + username.isSelected());
        System.out.println("Password field is selected: " + password.isSelected());
    }
    
    @Test(priority = 5)
    public void performIsEnabled() {
        WebElement loginbutton = getLoginButton();
        System.out.println("Login Button is enabled: " + loginbutton.isEnabled());
    }
    
    
    
}
