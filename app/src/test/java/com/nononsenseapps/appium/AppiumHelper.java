package com.nononsenseapps.appium;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;

public class AppiumHelper {

    public static By assertVisibleText(String text) {
        return By.xpath("//UIAStaticText[@visible=\"true\" and (@name=\"" + text
                + "\" or @hint=\"" + text + "\" or @label=\"" + text
                + "\" or @value=\"" + text + "\""
                + " or @text=\"" + text + "\"" + ")]");
    }

    public static void createNewNoteWithName(AndroidDriver<WebElement> driver, String name){
        driver.findElement(By.id("com.nononsenseapps.notepad:id/fab")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/taskText")).sendKeys(name);
    }

    public static void createNotes(AndroidDriver<WebElement> driver, String[] noteNames){
        for (int i = 0; i < noteNames.length; i++){
            createNewNoteWithName(driver, noteNames[i]);
            navigateUp(driver);
        }
    }

    public static void createTaskList(AndroidDriver<WebElement> driver, String name) {
        scrollInListTo(driver, "Create new");
        driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Create')]")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/titleField")).sendKeys(name);
        driver.findElement(By.id("com.nononsenseapps.notepad:id/dialog_yes")).click();
    }


    public static List<WebElement> getNotesInNotesList(AndroidDriver<WebElement> driver) {
        return (List<WebElement>) new ArrayList<WebElement>(driver.findElementsByAccessibilityId("Item title"));
    }

    public static void navigateUp(AndroidDriver<WebElement> driver) {
        driver.findElementByAccessibilityId("Navigate up").click();

    }

    public static void closeDrawer(AndroidDriver<WebElement> driver){
        WebElement drawerLayout = driver.findElementById("com.nononsenseapps.notepad:id/navigation_drawer");

        //find the middle point of the drawer layout
        Dimension dimension = drawerLayout.getSize();
        Point point = new Point(dimension.getWidth()/2, dimension.getHeight()/2);

        //swipe from the middle to left edge to close drawer
        driver.swipe(point.getX(), point.getY(), 1, point.getY(), 300);
    }

    public static WebElement scrollInListTo(AndroidDriver driver, String elementName){
        //apparently this is the way to do it, according to
        // https://github.com/appium/java-client/issues/421
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)" +
                ".instance(0)).scrollIntoView(new UiSelector()" +
                ".textContains(\"" + elementName + "\").instance(0))" );
    }

    public static void openDrawer(AndroidDriver<WebElement> driver) {

        driver.findElementByAccessibilityId("Open navigation drawer").click();
    }
}
