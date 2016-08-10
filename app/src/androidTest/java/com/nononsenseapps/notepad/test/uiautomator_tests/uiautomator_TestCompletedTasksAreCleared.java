package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;

public class uiautomator_TestCompletedTasksAreCleared extends BaseTestClass{

    private String noteName1 = "prepare food";
    private String noteName2 = "take dogs out";
    private String noteName3 = "water plants";
    private String noteName4 = "sleep";
    private String[] noteNames = {noteName1, noteName2, noteName3, noteName4};

    @Test
    public void testCompletedTasksAreCleared() throws Exception{

        uiautomator_helper.closeDrawer(device);
        uiautomator_helper.createNotes(device, noteNames);

        //check the checkboxes
        List<UiObject2> checkBoxes = device.findObjects(By.clazz("android.widget.CheckBox"));
        checkBoxes.get(1).click();
        checkBoxes.get(3).click();

        UiObject note2 = device.findObject(new UiSelector().text(noteNames[0]));
        UiObject note4 = device.findObject(new UiSelector().text(noteNames[2]));

        //clear done tasks
        device.findObject(By.descContains("More options")).click();

        UiObject clearDoneTasks = device.findObject(
                new UiSelector().text("Clear completed"));

        if(clearDoneTasks.waitForExists(LAUNCH_TIMEOUT))
            clearDoneTasks.click();
        device.findObject(By.text("OK")).click();

        //assert notes are no more visible
        assertFalse("Note with name " + noteNames[0] + " should not be visible", note2.exists());
        assertFalse("Note with name " + noteNames[2] + " should not be visible", note4.exists());

    }

}
