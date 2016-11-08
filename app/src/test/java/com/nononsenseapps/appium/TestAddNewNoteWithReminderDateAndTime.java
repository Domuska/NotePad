package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestAddNewNoteWithReminderDateAndTime extends BaseTestClass{

    @Test
    public void testAddNewNoteWithReminderDateAndTime(){

        AppiumHelper.closeDrawer(driver);
        AppiumHelper.createNewNoteWithName(driver, noteName1);
        driver.hideKeyboard();

        //add reminder
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationAdd")).click();

        //add date
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationDate")).click();
        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='Done']"))
        );
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();

        //add time
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationTime")).click();

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.id("com.nononsenseapps.notepad:id/done_button"))
        );

        driver.findElement(By.id("com.nononsenseapps.notepad:id/done_button")).click();

        AppiumHelper.navigateUp(driver);

        //check that the date field is visible
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='" + noteName1 + "']"))).click();
        //try-catch since if the keyboard is not visible, this might throw an exception, weirdly
        try {
            driver.hideKeyboard();
        }catch(WebDriverException e){
            System.out.println("Problem with closing keyboard");
        }
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationDate"));
    }
}
