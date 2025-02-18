package overallTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

// Page Object Model: Login Page
class LoginPage {
    WebDriver driver;
    
    @FindBy(id = "user-name") WebElement username;
    @FindBy(id = "password") WebElement password;
    @FindBy(id = "login-button") WebElement loginButton;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void login(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        loginButton.click();
    }
}

// Page Object Model: Cart Page
class CartPage {
    WebDriver driver;
    
    @FindBy(className = "shopping_cart_link") WebElement cart;
    @FindBy(id = "checkout") WebElement checkout;
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void goToCart() {
        cart.click();
    }
    
    public void proceedToCheckout() {
        checkout.click();
    }
}

// Page Object Model: Checkout Page
class CheckoutPage {
    WebDriver driver;
    
    @FindBy(id = "first-name") WebElement firstName;
    @FindBy(id = "last-name") WebElement lastName;
    @FindBy(id = "postal-code") WebElement postalCode;
    @FindBy(id = "continue") WebElement continueButton;
    @FindBy(id = "finish") WebElement finishButton;
    
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void completeCheckout(String fName, String lName, String zip) {
        firstName.sendKeys(fName);
        lastName.sendKeys(lName);
        postalCode.sendKeys(zip);
        continueButton.click();
        finishButton.click();
    }
}

// Test Class
public class SaucedemoTest {
    WebDriver driver;
    LoginPage loginPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    
    @BeforeClass
    public void setup() {

    	WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }
    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"standard_user", "secret_sauce"},
//            {"locked_out_user", "secret_sauce"}
        };
    }
    
    @Test(priority = 1, dataProvider = "loginData")
    public void loginWithValidCredentials(String username, String password) {
        loginPage.login(username, password);
        
        WebElement inventory = driver.findElement(By.className("inventory_list"));
        Assert.assertTrue(inventory.isDisplayed(), "Login failed");
        
        
    }
    
    @Test(priority = 2)
    public void addToCart() {
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "1", "Item not added to cart");
        
        
    }
    
    
    
    @Test(priority = 3)
    public void checkoutProcess() {
        cartPage.goToCart();
        cartPage.proceedToCheckout();
        checkoutPage.completeCheckout("John", "Doe", "12345");
        
        WebElement confirmation = driver.findElement(By.className("complete-header"));
        Assert.assertTrue(confirmation.isDisplayed(), "Checkout failed");
        
        
    }
    
    @Test(priority = 4)
    public void logout() {
    	
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
        
        driver.findElement(By.id("react-burger-menu-btn")).click();
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=\"Logout\"]")));
	    
        
//        driver.findElement(By.id("back-to-products")).click();
        
        
        driver.findElement(By.xpath("//a[text()=\"Logout\"]")).click();
        
        WebElement loginPage = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginPage.isDisplayed(), "Logout failed");

    }
    
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}