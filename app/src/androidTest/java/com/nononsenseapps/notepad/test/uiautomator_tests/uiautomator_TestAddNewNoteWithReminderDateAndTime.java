package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class uiautomator_TestAddNewNoteWithReminderDateAndTime extends BaseTestClass{

    UiDevice device;
    private String noteName1 = "prepare food";

    @Before
    public void setUp(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiautomator_helper.startApplication(device);
    }



    @Test
    public void testAddNewNoteWithReminderDateAndTime() throws Exception{

        uiautomator_helper.closeDrawer(device);
        uiautomator_helper.createNewNoteWithName(device, noteName1);

        if(isKeyboardOpened())
            device.pressBack();

        //add reminder
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/notificationAdd"))
                .click();

        //add date
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/notificationDate"))
                .click();

        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/done"))
                .click();

        //add time
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/notificationTime"))
                .click();

        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/done_button"))
                .click();

        uiautomator_helper.navigateUp(device);
        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")), LAUNCH_TIMEOUT);

        //check that the date field is visible
        device.findObject(new UiSelector().text(noteName1)).click();

        if(isKeyboardOpened())
            device.pressBack();

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/notificationDate")
        ), LAUNCH_TIMEOUT);

        UiObject uiObject2 = device.findObject(
                new UiSelector().resourceId("com.nononsenseapps.notepad:id/notificationDate"));

        assertNotNull(uiObject2);
    }
}
