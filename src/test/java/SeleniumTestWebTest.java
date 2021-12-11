// Generated by Selenium IDE
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.BeforeAll;
import org.junit.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.junit.Before;
import org.junit.After;
//import io.cucumber.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;

import java.sql.Driver;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumTestWebTest {
  public static WebDriver driver = new ChromeDriver();
  private Map<String, Object> vars;
  JavascriptExecutor js;

  @BeforeMethod
  public void setUp() {
    /*
         Run -> Edit Config -> VM Options :
        -Dwebdriver.chrome.driver="D:\\Selenium\\chromedriver.exe"
    */
    System.setProperty("webdriver.chrome.driver","D:\\Selenium\\chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void seleniumTestweb() {
    //System.setProperty("webdriver.chrome.driver","D:\\Selenium\\chromedriver.exe");
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1058, 805));
    driver.findElement(By.cssSelector("div:nth-child(2) > a:nth-child(2) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(1) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(3) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(1) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(4) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(1) > button")).click();
    driver.findElement(By.cssSelector("div > a:nth-child(5) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(1) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(6) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(1) > button")).click();
    driver.findElement(By.cssSelector("div:nth-child(3) button")).click();
    driver.findElement(By.cssSelector("a:nth-child(1) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(3) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(3) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(4) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(5) > button")).click();
    driver.findElement(By.cssSelector("a:nth-child(1) > button")).click();
    driver.findElement(By.cssSelector("body > a > button")).click();
  }

  public WebDriver getDriverInstance(){
    return this.driver;
  }

}
