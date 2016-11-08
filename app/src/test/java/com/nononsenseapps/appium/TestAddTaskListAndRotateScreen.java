package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestAddTaskListAndRotateScreen extends BaseTestClass{

    @Test
    public void testAddTaskListAndRotateScreen(){
        AppiumHelper.createTaskList(driver, taskListName);

        AppiumHelper.openDrawer(driver);

        driver.rotate(ScreenOrientation.LANDSCAPE);
        driver.rotate(ScreenOrientation.PORTRAIT);
        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='" + taskListName + "']"))
        );
    }
}
