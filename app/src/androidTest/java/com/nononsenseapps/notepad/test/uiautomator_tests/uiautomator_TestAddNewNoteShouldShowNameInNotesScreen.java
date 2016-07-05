package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class uiautomator_TestAddNewNoteShouldShowNameInNotesScreen extends BaseTestClass{


    private static final String NOTEPAD_TEST_PACKAGE
            = "com.nononsenseapps.notepad.test.uiautomator_tests";
    private UiDevice device;
    private String noteName1 = "prepare food";

    @Before
    public void setUp(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiautomator_helper.startApplication(device);
    }

    @After
    public void tearDown2(){
        try {
            device.pressHome();

            InstrumentationRegistry.getInstrumentation().getUiAutomation()
                    .executeShellCommand("pm clear com.nononsenseapps.notepad")
                    .close();




//            InstrumentationRegistry.getInstrumentation().getUiAutomation()
//                .executeShellCommand("am start -W -n com.nononsenseapps.notepad/.activities.ActivityList")
//                .close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

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
