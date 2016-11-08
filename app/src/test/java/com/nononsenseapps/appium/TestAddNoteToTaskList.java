package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.Assert.assertTrue;

public class TestAddNoteToTaskList extends BaseTestClass{

    @Test
    public void testAddNoteToTaskList(){
        AppiumHelper.createTaskList(driver, taskListName);

        //open the task list
        AppiumHelper.openDrawer(driver);
        driver.findElement(By.xpath("//*[@text='"+ taskListName +"']")).click();

        //add the note
        AppiumHelper.createNewNoteWithName(driver, noteName1);
        AppiumHelper.navigateUp(driver);

        //assert the drawer shows the task list has a task
        AppiumHelper.openDrawer(driver);
        List<WebElement> elements = driver.findElements(By.id("android:id/text2"));

        boolean taskNumberFound = false;
        for(WebElement element : elements){
            if(element.getText().equals("1"))
                taskNumberFound = true;
        }
        assertTrue("Number of tasks in task list is not found" , taskNumberFound);
    }
}
