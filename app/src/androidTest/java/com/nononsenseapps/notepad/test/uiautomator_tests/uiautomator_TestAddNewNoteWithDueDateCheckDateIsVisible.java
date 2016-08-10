package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;


public class uiautomator_TestAddNewNoteWithDueDateCheckDateIsVisible extends BaseTestClass{

    private String noteName1 = "prepare food";

    @Test
    public void testAddNewNoteWithDueDateCheckDateIsVisible() throws Exception{

        uiautomator_helper.closeDrawer(device);

        uiautomator_helper.createNewNoteWithName(device, noteName1);

        if (isKeyboardOpened())
            device.pressBack();

        //add the reminder to this date and time
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/dueDateBox"))
                .click();
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/done"))
                .click();

        uiautomator_helper.navigateUp(device);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/date")
        ), LAUNCH_TIMEOUT);

        UiObject object = device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/date"));

        assertNotNull("Due date field not found", object);
    }
}
