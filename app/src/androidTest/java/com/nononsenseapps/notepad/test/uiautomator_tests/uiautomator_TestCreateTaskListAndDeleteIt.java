package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;

public class uiautomator_TestCreateTaskListAndDeleteIt extends BaseTestClass{

    private String taskListName = "a random task list";

    @Test
    public void testCreateTaskListAndDeleteIt() throws Exception{

        uiautomator_helper.createTaskList(device, taskListName);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")), LAUNCH_TIMEOUT);

        uiautomator_helper.openDrawer(device);

        //delete the task list
        device.findObject(By.text(taskListName)).longClick();

        UiObject deleteButton = device.findObject(
                new UiSelector().resourceId("com.nononsenseapps.notepad:id/deleteButton"));

        if(deleteButton.waitForExists(LAUNCH_TIMEOUT))
            deleteButton.click();
        device.wait(Until.findObject(By.text("OK")), GENERAL_TIMEOUT).click();

        //check that the list is not found
        UiObject object = device.findObject(new UiSelector().text(taskListName));

        assertFalse("Task list should be deleted", object.exists());

    }
}
