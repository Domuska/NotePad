package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class uiautomator_TestAddNoteToTaskList {

    private static final String NOTEPAD_TEST_PACKAGE
            = "com.nononsenseapps.notepad.test.uiautomator_tests";
    private UiDevice device;
    private static final String NOTEPAD_PACKAGE = "com.nononsenseapps.notepad";
    private static final int LAUNCH_TIMEOUT = 5000;

    private String taskListName = "a random task list";
    private String noteName1 = "prepare food";

    @Before
    public void setUp(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiautomator_helper.startApplication(device);
    }

    @Test
    public void testAddNoteToTaskList() throws Exception{

        uiautomator_helper.createTaskList(device, taskListName);
        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")), LAUNCH_TIMEOUT);

        uiautomator_helper.openDrawer(device);

        device.findObject(By.text(taskListName)).click();

        uiautomator_helper.createNewNoteWithName(device, noteName1);
        UiObject2 object = device.findObject(By.text(noteName1));
        assertNotNull("Note not found", object);
    }
}
