package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;

public class uiautomator_TestCreateNoteAndDeleteIt extends BaseTestClass{

    private String noteName1 = "prepare food";

    @Test
    public void testCreateNoteAndDeleteIt() throws Exception{

        //close drawer and create the note
        uiautomator_helper.closeDrawer(device);
        uiautomator_helper.createNewNoteWithName(device, noteName1);
        uiautomator_helper.navigateUp(device);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")
        ), LAUNCH_TIMEOUT);

        //open the note and delete it
        device.findObject(new UiSelector().text(noteName1)).click();
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/menu_delete")).click();
        device.findObject(new UiSelector().resourceId("android:id/button1")).click();

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")
        ), LAUNCH_TIMEOUT);

        //assert the note is gone
        UiObject uiObject = device.findObject(new UiSelector().text(noteName1));
        assertFalse("Note is still visible", uiObject.exists());
    }
}
