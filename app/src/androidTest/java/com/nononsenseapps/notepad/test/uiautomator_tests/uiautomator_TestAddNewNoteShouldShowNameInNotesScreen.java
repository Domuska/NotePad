package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class uiautomator_TestAddNewNoteShouldShowNameInNotesScreen extends BaseTestClass{

    private String noteName1 = "prepare food";

    @Test
    public void testAddNewNoteShouldShowNameInNotesScreen() throws Exception{

        uiautomator_helper.closeDrawer(device);
        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")), LAUNCH_TIMEOUT);

        uiautomator_helper.createNewNoteWithName(device, noteName1);


        uiautomator_helper.navigateUp(device);

        device.wait(Until.hasObject(By.text(noteName1)), LAUNCH_TIMEOUT);

        UiObject2 object = device.findObject(By.text(noteName1));
        assertNotNull("Note not found", object);
    }
}
