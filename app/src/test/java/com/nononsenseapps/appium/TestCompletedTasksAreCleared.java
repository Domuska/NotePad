package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;

public class TestCompletedTasksAreCleared extends BaseTestClass{

    @Test
    public void testCompletedTasksAreCleared(){
        AppiumHelper.closeDrawer(driver);

        String [] noteNames = {noteName1, noteName2, noteName3, noteName4};
        AppiumHelper.createNotes(driver, noteNames);

        List<WebElement> checkBoxes =  driver.findElements(By.id("com.nononsenseapps.notepad:id/checkbox"));
        checkBoxes.get(1).click();
        checkBoxes.get(3).click();

        driver.findElementByAccessibilityId("More options").click();
        driver.findElement(By.xpath("//*[@text='Clear completed']")).click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='OK']"))).click();
        //driver.findElement(By.xpath("//*[@text='OK']")).click();

        List<WebElement> remainingTasks = AppiumHelper.getNotesInNotesList(driver);
        List<String> noteTitles = new ArrayList<String>();
        for(int i = 0; i < noteTitles.size(); i++){
            noteTitles.add(remainingTasks.get(i).getText());
        }

        assertFalse(noteTitles.contains(noteNames[1]));
        assertFalse(noteTitles.contains(noteNames[3]));
    }
}
