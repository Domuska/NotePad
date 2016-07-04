package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class uiautomator_TestAddNotesAndRotateScreen extends BaseTestClass{

    UiDevice device;
    private String noteName1 = "prepare food";
    private String noteName2 = "take dogs out";
    private String noteName3 = "water plants";
    private String noteName4 = "sleep";
    private String[] noteNames = {noteName1, noteName2, noteName3, noteName4};

    @Before
    public void setUp(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiautomator_helper.startApplication(device);
    }

    @Test
    public void testAddNotesAndRotateScreen() throws Exception{

        //create notes
        uiautomator_helper.closeDrawer(device);
        uiautomator_helper.createNotes(device, noteNames);

        //rotate device
        device.setOrientationLeft();
        device.unfreezeRotation();
        device.setOrientationNatural();
        device.unfreezeRotation();

        //assert notes still visible
        UiObject note1 = device.findObject(new UiSelector().text(noteName1));
        UiObject note2 = device.findObject(new UiSelector().text(noteName2));
        UiObject note3 = device.findObject(new UiSelector().text(noteName3));
        UiObject note4 = device.findObject(new UiSelector().text(noteName4));

        assertTrue("Note " + noteName1 + " not found", note1.exists());
        assertTrue("Note " + noteName2 + " not found", note2.exists());
        assertTrue("Note " + noteName3 + " not found", note3.exists());
        assertTrue("Note " + noteName4 + " not found", note4.exists());

    }
}
