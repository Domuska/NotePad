package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;

public class TestAddTaskListCheckItIsAddedToDrawer extends BaseTestClass{

    @Test
    public void testAddTaskListCheckItIsAddedToDrawer(){

        AppiumHelper.createTaskList(driver, taskListName);

        driver.findElementByAccessibilityId("Open navigation drawer").click();

        driver.findElement
                (By.xpath("//*[@text='" + taskListName + "']"));

        //should modify this test so that we check the visibility of the element
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
//                assertVisibleText(taskListName)
//        ));
    }
}
