package com.nononsenseapps.notepad.test.uiautomator_faulty_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;

import com.nononsenseapps.notepad.test.uiautomator_tests.BaseTestClass;
import com.nononsenseapps.notepad.test.uiautomator_tests.uiautomator_helper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.fail;

public class uiautomator_FaultyTests extends BaseTestClass {

    UiDevice device;
    private String noteName1 = "prepare food";

    @Before
    public void setUp(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiautomator_helper.startApplication(device);
    }

    @Test
    @Ignore
    public void testAddNewNoteSearchForFaultyNoteName() throws Exception{
        uiautomator_helper.closeDrawer(device);

        uiautomator_helper.createNewNoteWithName(device, noteName1);
        uiautomator_helper.navigateUp(device);

        device.findObject(By.text(noteName1 + "asdf"));

    }

    @Test
    @Ignore
    public void testSearchForElementWithTextShouldFailOnView() throws Exception{

        uiautomator_helper.closeDrawer(device);
        device.findObject(By.text("Create new"));

    }

    //search for element with valid ID but that should not be visible
    @Test
    @Ignore
    public void testSearchForElementWithIDShouldFailOnView() throws Exception{
        uiautomator_helper.closeDrawer(device);
        device.findObject(By.res("com.nononsenseapps.notepad:id/fab")).click();
        device.findObject(By.res("com.nononsenseapps.notepad:id/fab")).click();

    }

    @Test
    @Ignore
    public void testSearchForElementWithFaultyID() throws Exception{

        uiautomator_helper.closeDrawer(device);
        device.findObject(By.res("com.nononsenseapps.notepad:id" + "asdf"));
    }

    @Test
    @Ignore
    public void testSearchForElementWithAmbiguousIdentifier() throws Exception{

        uiautomator_helper.closeDrawer(device);

        uiautomator_helper.createNewNoteWithName(device, noteName1);
        uiautomator_helper.navigateUp(device);

        uiautomator_helper.createNewNoteWithName(device, noteName1);
        uiautomator_helper.navigateUp(device);

        device.findObject(By.text(noteName1)).click();
    }



}
