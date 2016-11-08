package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static junit.framework.Assert.assertTrue;

public class TestAddNotesAndRotateScreen extends BaseTestClass{

    @Test
    public void testAddNotesAndRotateScreen(){
        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};

        AppiumHelper.closeDrawer(driver);
        AppiumHelper.createNotes(driver, noteNames);

        //rotate screen
        driver.rotate(ScreenOrientation.LANDSCAPE);
        driver.rotate(ScreenOrientation.PORTRAIT);

        assertTrue("element: " + noteNameList[0] + " is not displayed",
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@text='"+ noteNameList[0] + "']")))
                        .isDisplayed());
        assertTrue("element: " + noteNameList[1] + " is not displayed",
                driver.findElement(By.xpath("//*[@text='"+ noteNameList[1] + "']")).isDisplayed());
        assertTrue("element: " + noteNameList[2] + " is not displayed",
                driver.findElement(By.xpath("//*[@text='"+ noteNameList[2] + "']")).isDisplayed());
        assertTrue("element: " + noteNameList[3] + " is not displayed",
                driver.findElement(By.xpath("//*[@text='"+ noteNameList[3] + "']")).isDisplayed());
    }
}
