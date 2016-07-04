package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class uiautomator_TestAddTaskListAndRotateScreen extends BaseTestClass{

    private UiDevice device;
    private String taskListName = "a random task list";

    @Before
    public void setUp(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiautomator_helper.startApplication(device);
    }

    @After
    public void resetOrientation() throws Exception{
    }

    @Test
    public void testAddTaskListAndRotateScreen() throws Exception{

        uiautomator_helper.createTaskList(device, taskListName);
        uiautomator_helper.openDrawer(device);

        device.setOrientationLeft();
        device.unfreezeRotation();

        device.setOrientationNatural();
        device.unfreezeRotation();

        UiObject taskListNameObject = device.findObject(new UiSelector().text(taskListName));

        if(!taskListNameObject.waitForExists(LAUNCH_TIMEOUT))
            fail("task list not after screen rotation");
    }
}
