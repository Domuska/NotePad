package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class uiautomator_TestCompletedTasksAreCleared extends BaseTestClass{

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
    public void testCompletedTasksAreCleared() throws Exception{

        uiautomator_helper.closeDrawer(device);
        uiautomator_helper.createNotes(device, noteNames);

        List<UiObject2> checkBoxes = device.findObjects(By.clazz("android.widget.CheckBox"));
        checkBoxes.get(1).click();
        checkBoxes.get(3).click();

        UiObject2 note2 = device.findObject(By.text(noteNames[1]));
        UiObject2 note4 = device.findObject(By.text(noteNames[3]));

        
    }

}
