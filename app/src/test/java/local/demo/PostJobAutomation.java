package local.demo;

import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoAlertPresentException;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class PostJobAutomation {
    WebDriver driver;

    @BeforeSuite
    public void createDriver() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        // Set ChromeOptions with preferences to allow geolocation
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.geolocation", 1); // 1: allow
        prefs.put("profile.default_content_setting_values.notifications", 1); // 1: allow

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://hireanartist.com.au/post-a-job");
        Thread.sleep(5000);
    }

    @Test
    public void fillJobForm() throws InterruptedException, AWTException {
        // Click on cookie Button
        WebElement cookieButton = driver.findElement(By.xpath("//button[text()='I Agree.']"));
        cookieButton.click();
        Thread.sleep(2000);

        // Fill in the job title
        WebElement jobTitle = driver.findElement(By.xpath("//input[@id='project_name']"));
        jobTitle.sendKeys("Public Window Mural - Canada");
        Thread.sleep(2000);

        // Select the type of art requested
        WebElement artType = driver.findElement(By.xpath("//span[text()='Mural/Graffiti']"));
        artType.click();
        Thread.sleep(2000);

        // Select the mural location
        WebElement muralLocation = driver.findElement(By.xpath("//input[@value='Window']"));
        muralLocation.click();
        Thread.sleep(2000);

        // Select the size (example selection of meters and input values)
        WebElement width = driver.findElement(By.xpath("//input[@id='width']"));
        width.sendKeys("5");
        Thread.sleep(2000);
        WebElement height = driver.findElement(By.xpath("//input[@id='height']"));
        height.sendKeys("5");
        Thread.sleep(2000);
        WebElement feetButton = driver.findElement(By.xpath("//span[text()='ft.']"));
        feetButton.click();
        Thread.sleep(2000);

        // Fill in the location of the project
        WebElement location = driver.findElement(By.xpath("//input[@placeholder='Search your location...']"));
        location.click();
        location.sendKeys(("Canada"));
        Thread.sleep(2000);

        // Locate all options in the dropdown
        List<WebElement> locationOptions = driver
                .findElements(By.xpath("//span[@aria-label='environment']//following::span"));

        // Iterate through the options and select the one that matches
        for (WebElement option : locationOptions) {
            if (option.getText().contains("Canada Bay NSW, Australia")) {
                option.click();
                break;
            }
        }

        // Select the type of property
        WebElement propertyType = driver.findElement(By.xpath("//span[text()='Public/Government']"));
        propertyType.click();
        Thread.sleep(2000);

        // Click on continue button to move to Design Page
        WebElement continueButton = driver.findElement(By.xpath("//span[text()='Continue']"));
        continueButton.click();
        Thread.sleep(2000);

        // On the Design page, select "No idea"
        WebElement noIdeaOption = driver.findElement(By.xpath("//input[@value='No idea']"));
        noIdeaOption.click();
        Thread.sleep(2000);

        // Provide details about the design idea
        WebElement designDetails = driver.findElement(By.xpath("//textarea[@id='job_description']"));
        designDetails.sendKeys(
                "We are looking for a vibrant mural that includes themes of nature and urban life. The subjects should include local flora and fauna, and the colors should be bright and engaging.");
        Thread.sleep(2000);

        // Select maximum 3 tags from a list
        List<WebElement> tags = driver
                .findElements(By.xpath("//div[@class='project-tag__container']//following::span"));
        String[] desiredTags = { "Nature and Animals", "Movie characters", "abstract" };

        for (WebElement tag : tags) {
            for (String desiredTag : desiredTags) {
                if (tag.getText().equals(desiredTag)) {
                    tag.click();
                    Thread.sleep(2000);
                }
            }
        }

        // // Upload reference photos
        // WebElement uploadInput = driver.findElement(By.xpath("//button[@title='Attach
        // file']"));
        // uploadInput.click();
        // uploadInput.sendKeys("C:\\Users\\saura\\Downloads\\DesignPage.pdf");
        // Thread.sleep(5000);

        // Upload reference photos
        WebElement uploadInput = driver.findElement(By.xpath("//button[@title='Attach file']"));
        uploadInput.click();
        Thread.sleep(2000);

        // Use Robot class to handle file upload
        StringSelection filePath = new StringSelection("C:\\Users\\saura\\Downloads\\DesignPage.pdf");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);

        Robot robot = new Robot();
        robot.delay(2000);

        // Press Ctrl+V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(5000);

        // Click on continue button to move to Budget Page
        WebElement continueButton1 = driver.findElement(By.xpath("//span[text()='Continue']"));
        continueButton1.click();
        Thread.sleep(2000);

        // Enter approximate budget
        WebElement budgetInput = driver.findElement(By.xpath("//input[@id='budget']"));
        budgetInput.sendKeys("100.50");
        Thread.sleep(2000);

        // Select currency from dropdown
        WebElement currencyDropdown = driver.findElement(By.xpath("//span[@class='ant-select-selection-item']"));
        currencyDropdown.click();
        Thread.sleep(2000);

        // Locate the currency options
        List<WebElement> currencyOptions = driver.findElements(By.xpath(
                "//div[contains(@class, 'rc-virtual-list-holder-inner')]//div[@class='ant-select-item-option']"));

        // Iterate through the options and select CAD
        for (WebElement option : currencyOptions) {
            WebElement optionContent = option
                    .findElement(By.xpath(".//span[contains(@class, 'ant-select-item-option-content')]"));
            if (optionContent.getText().equals("CAD")) {
                option.click();
                break;
            }
        }
        Thread.sleep(2000);
        // // Locate the currency options
        // List<WebElement> currencyOptions =
        // driver.findElements(By.xpath("//div[contains(@class,
        // 'rc-virtual-list-holder-inner')]//div[@class='ant-select-item-option-content']"));

        // // Iterate through the options and select CAD

        // List<WebElement> currencyOptions =
        // currencyDropdown.findElements(By.xpath("//div[@class='ant-select-item-option-content']//following::span"));

        // for (WebElement option : currencyOptions) {
        // if (option.getText().equals("CAD")) {
        // System.out.println(option.getText());
        // ((JavascriptExecutor)
        // driver).executeScript("arguments[0].scrollIntoView(true);", option);
        // Thread.sleep(2000);
        // option.click();
        // break;
        // }
        // }

        // Select the artwork completion date
        WebElement datePicker = driver.findElement(By.xpath("//input[@placeholder='On date']"));
        datePicker.click();
        WebElement selectDate = driver
                .findElement(By.xpath("(//div[@class='ant-picker-cell-inner'][normalize-space()='30'])[2]"));
        selectDate.click();
        Thread.sleep(2000);

        // Click on continue button to move to the next page
        WebElement continueButtonTimeline = driver.findElement(By.xpath("//span[text()='Continue']"));
        continueButtonTimeline.click();
        Thread.sleep(5000);

        // Select "An Individual" option for representing organization
        WebElement individualOption = driver.findElement(By.xpath("//span[text()='An Individual']"));
        individualOption.click();
        Thread.sleep(2000);

        // Select country for phone number
        WebElement countryDropdown = driver.findElement(By.xpath("//select[@aria-label='Phone number country']"));
        countryDropdown.click();
        List<WebElement> countryOptions = driver
                .findElements(By.xpath("//select[@aria-label=\"Phone number country\"]//following::option"));
        for (WebElement option : countryOptions) {
            if (option.getText().equals("India")) {
                option.click();
                break;
            }
        }
        Thread.sleep(2000);

        // Enter phone number
        WebElement phoneNumber = driver.findElement(By.xpath("//input[@id='mobile']"));
        phoneNumber.sendKeys("9934567890");
        Thread.sleep(2000);

        // Enter personal details
        WebElement firstName = driver.findElement(By.xpath("//input[@id='first_name']"));
        firstName.sendKeys("Saurabh");
        Thread.sleep(2000);

        WebElement lastName = driver.findElement(By.xpath("//input[@id='last_name']"));
        lastName.sendKeys("Kumar");
        Thread.sleep(2000);

        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys("saurabhkumar993973@gmail.com");
        Thread.sleep(2000);

        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("Saurabh123");
        Thread.sleep(2000);

        WebElement agreeButton = driver.findElement(By.xpath("//input[@id='terms_privacy']"));
        agreeButton.click();
        Thread.sleep(10000);

        // Handle Captcha
        // driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']")));
        // Thread.sleep(5000);
        // WebElement
        // captchaaBox=driver.findElement(By.xpath("//div[@class='recaptcha-checkbox-border']"));
        // captchaaBox.click();
        // Thread.sleep(10000);
        // driver.switchTo().defaultContent();

        // Submit Button
        WebElement submitButton = driver.findElement(By.xpath("//span[text()='Save & submit']"));
        submitButton.click();
        Thread.sleep(2000);

    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
