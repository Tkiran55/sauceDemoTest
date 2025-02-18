package saucedemo;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class Setup {

    public WebDriver driver;
    public static String url = "https://www.saucedemo.com/";

    @BeforeClass
    public void open_browser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }
    
    @Test(priority = 1)
    public void testLoginPageCurrentUrl(){
    	String websiteUrl = driver.getCurrentUrl();
    	System.out.println("Website Url: " + websiteUrl);
    	assert websiteUrl.equals(url);
    	
    }
    
    @Test(priority = 2)
    public void testLoginPageTitle() {
    	String title = driver.getTitle();
    	System.out.println("Title is: " + title);
    	assert title.contains("Swag Labs");
    }
    

    @AfterClass
    public void close_browser() throws InterruptedException {
    	Thread.sleep(5000);
        if (driver != null) {
            driver.quit();
        }
    }
}
