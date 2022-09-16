package AccessibilityTests;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;

public class AccessibilityTest {

     WebDriver driver;
     private static final URL scriptUrl = AccessibilityTest.class.getResource("/axe.min.js");

@BeforeMethod
     public void setup(){
     WebDriverManager.chromedriver().setup();
     driver = new ChromeDriver();
     driver.get("https://www.amazon.com");
}

@Test
public void AccessibilityTest() {
     JSONObject responseJson = new AXE.Builder(driver, scriptUrl)
             //.include("title") //Commenting this will fail the test with lists of "accessibility violations errors"
             .analyze();
     JSONArray violations = responseJson.getJSONArray("violations");

     if (violations.length() == 0) {
          System.out.println("No errors");
     }
     else{
          AXE.writeResults("accessibilityTest",responseJson);
          Assert.assertTrue(false, AXE.report(violations));
     }
}

@AfterMethod
     public void teardown(){
     driver.quit();
}

}
