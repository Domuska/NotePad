package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class uiautomator_TestAddNoteToTaskList extends BaseTestClass{

    private String taskListName = "a random task list";
    private String noteName1 = "prepare food";

    @Test
    public void testAddNoteToTaskList() throws Exception{

        uiautomator_helper.createTaskList(device, taskListName);
        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")), LAUNCH_TIMEOUT);

        uiautomator_helper.openDrawer(device);

        //open the task list and add new note to it
        device.findObject(new UiSelector().text(taskListName))
                .clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        uiautomator_helper.createNewNoteWithName(device, noteName1);
        uiautomator_helper.navigateUp(device);

        //assert the drawer shows the task list has a task
        uiautomator_helper.openDrawer(device);
        List<UiObject2> objects = device.findObjects(By.res("android:id/text2"));

        boolean tasksNumberFound = false;
        for(UiObject2 object : objects){
            if(object.getText().equals("1"))
                tasksNumberFound = true;
        }
        assertTrue("Number of tasks in task list is not found" , tasksNumberFound);
    }
}
