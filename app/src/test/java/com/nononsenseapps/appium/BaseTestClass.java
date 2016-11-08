package com.nononsenseapps.appium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseTestClass {


    protected AndroidDriver<WebElement> driver;
    protected WebDriverWait driverWait;

    protected final String TASK_LIST_TITLE = "Notes";
    protected String taskListName = "a random task list";
    protected String noteName1 = "prepare food";
    protected String noteName2 = "take dogs out";
    protected String noteName3 = "water plants";
    protected String noteName4 = "sleep";
    protected String[] noteNameList = {noteName1, noteName2, noteName3, noteName4,
            "go for a jog", "do some work", "play with the dog",
            "work out", "do weird stuff", "read a book", "drink water",
            "write a book", "proofread the book", "publish the book",
            "ponder life", "build a house", "repair the house", "call contractor",
            "write another book", "scrap the book project", "start a blog",
            "  ", "     "};
    protected String[] taskListNames = {"Lorem", "ipsum ", "dolor ", "sit ", "amet", "consectetur ",
            "adipiscing ", "elit", "sed ", "do ", "eiusmod ", "tempor ", "incididunt ",
            "ut ", "labore "};

    @Before
    public void setUp() throws Exception{
//        File classpathRoot = new File(System.getProperty("user.dir"));
//        File app = new File(classpathRoot, "nononsensenotes-debug.apk");
//        System.out.println(app.getAbsolutePath());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Nexus 5x 1");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability(MobileCapabilityType.APP,
                "C:\\Users\\Tomi\\Projects\\notepad_own_fork\\nononsensenotes-6.0.0-beta.5-52-g634199b-free-debug-unaligned.apk");
        capabilities.setCapability("appPackage", "com.nononsenseapps.notepad");
        capabilities.setCapability("appActivity", ".activities.ActivityList");

        //commands to shut down the app and clear app data between tests
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("noReset", false);

        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driverWait = new WebDriverWait(driver, 20);

        driver.launchApp();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("com.nononsenseapps.notepad:id/navigation_drawer")
        ));
    }

    @After
    public void tearDown() throws Exception{
        if(driver != null)
            driver.quit();
    }
}
