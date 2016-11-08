package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import io.appium.java_client.TouchAction;

import static junit.framework.Assert.assertEquals;

public class TestCreateTaskListAndDeleteIt extends BaseTestClass{

    @Test
    public void testCreateTaskListAndDeleteIt(){

        //create the tasklist
        AppiumHelper.createTaskList(driver, taskListName);
        AppiumHelper.openDrawer(driver);

        //delete the tasklist
        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ taskListName + "']")
                )
        );

        //long press the element
        WebElement taskList = driver.findElement(By.xpath("//*[@text='"+ taskListName + "']"));
        TouchAction longPress = new TouchAction(driver);
        longPress.longPress(taskList, 2000).release().perform();

        driver.findElement(By.id("com.nononsenseapps.notepad:id/deleteButton")).click();

        driver.findElement(By.xpath("//*[@text='OK']")).click();

        //get the list of elements with the name that is used to create the element, should be 0
        List<WebElement> elements = driver.findElements(By.xpath("//*[@text='"+ taskListName + "']"));
        assertEquals(0, elements.size());
    }
}
