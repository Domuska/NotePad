package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestAddNewNoteWithDueDateCheckDateIsVisible extends BaseTestClass{

    @Test
    public void testAddNewNoteWithDueDateCheckDateIsVisible(){

        AppiumHelper.closeDrawer(driver);

        AppiumHelper.createNewNoteWithName(driver, noteName1);
        driver.hideKeyboard();

        driver.findElement(By.id("com.nononsenseapps.notepad:id/dueDateBox")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();
        AppiumHelper.navigateUp(driver);

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ noteName1 + "']")
                )
        );

        //should find the element even if its' visibility is set to invisible
        driver.findElement(By.id("com.nononsenseapps.notepad:id/date"));
    }
}
