package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestAddTaskListsScrollNavigationDrawer extends BaseTestClass{

    @Test
    public void testAddTaskListsScrollNavigationDrawer(){

        for(String name : taskListNames){
            AppiumHelper.createTaskList(driver, name);
            AppiumHelper.openDrawer(driver);
        }

        AppiumHelper.scrollInListTo(driver, "Settings").click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='Appearance']")
        ));
    }
}
