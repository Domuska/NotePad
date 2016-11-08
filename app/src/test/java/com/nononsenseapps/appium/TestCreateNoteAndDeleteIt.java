package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TestCreateNoteAndDeleteIt extends BaseTestClass{

    @Test
    public void testCreateNoteAndDeleteIt(){

        //first wait that the app is actually started
        driverWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("com.nononsenseapps.notepad:id/drawer_layout")
                )
        );

        //close drawer and create the note
        AppiumHelper.closeDrawer(driver);
        AppiumHelper.createNewNoteWithName(driver, noteName1);
        AppiumHelper.navigateUp(driver);

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ noteName1 + "']")
                )
        );

        //open the note and delete it
        driver.findElement(By.xpath("//*[@text='"+ noteName1 + "']")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/menu_delete")).click();
        driver.findElement(By.id("android:id/button1")).click();

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ TASK_LIST_TITLE + "']")
                )
        );

        //make sure the note is not visible any more
        List<WebElement> elements = driver.findElements(By.xpath("//*[@text='"+ noteName1 + "']"));
        assertEquals(0, elements.size());
    }
}
