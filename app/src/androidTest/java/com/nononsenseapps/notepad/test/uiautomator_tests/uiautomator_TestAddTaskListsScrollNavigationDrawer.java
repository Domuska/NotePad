package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class uiautomator_TestAddTaskListsScrollNavigationDrawer extends BaseTestClass{

    private String[] taskListNames = {"Lorem", "ipsum ", "dolor ", "sit ", "amet", "consectetur ",
            "adipiscing ", "elit", "sed ", "do ", "eiusmod ", "tempor ", "incididunt ",
            "ut ", "labore "};

    @Test
    public void testAddTaskListsScrollNavigationDrawer() throws Exception{

        for(String taskListName : taskListNames){
            uiautomator_helper.createTaskList(device, taskListName);
            uiautomator_helper.openDrawer(device);
        }

        UiScrollable recyclerView = new UiScrollable(
                new UiSelector().resourceId("com.nononsenseapps.notepad:id/navigation_drawer"));
        recyclerView.scrollTextIntoView("Settings");

        device.findObject(new UiSelector().text("Settings")).clickAndWaitForNewWindow(LAUNCH_TIMEOUT);

        assertTrue("Settings was not launched, text \"Appearance\" is not visible",
                device.findObject(new UiSelector().text("Appearance")).exists());
    }
}
