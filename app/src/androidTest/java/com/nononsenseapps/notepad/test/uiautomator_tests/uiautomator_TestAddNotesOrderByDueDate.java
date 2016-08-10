package com.nononsenseapps.notepad.test.uiautomator_tests;


import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.nononsenseapps.notepad.test.Helper;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class uiautomator_TestAddNotesOrderByDueDate extends BaseTestClass{

    private String noteName1 = "prepare food";
    private String noteName2 = "take dogs out";
    private String noteName3 = "water plants";
    private String noteName4 = "sleep";
    private String day04, day05, day15, day23;

    @Before
    public void setUp(){

        String currentMonthAndYear = uiautomator_helper.getMonthAndYear();

        day04 = "04 " + currentMonthAndYear;
        day05 = "05 " + currentMonthAndYear;
        day15 = "15 " + currentMonthAndYear;
        day23 = "23 " + currentMonthAndYear;
    }

    @Test
    public void testAddNotesOrderByDueDate() throws Exception{

        String[] expectedNoteOrder = {noteName2, noteName1, noteName4, noteName3};
        uiautomator_helper.closeDrawer(device);

        // day 05
        uiautomator_helper.createNewNoteWithName(device, noteName1);
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/dueDateBox"))
                .click();
        device.findObject(new UiSelector().descriptionContains(day05)).click();
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/done"))
                .click();
        uiautomator_helper.navigateUp(device);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")),
                LAUNCH_TIMEOUT);

        //day 04
        uiautomator_helper.createNewNoteWithName(device, noteName2);
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/dueDateBox"))
                .click();
        device.findObject(new UiSelector().descriptionContains(day04)).click();
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/done"))
                .click();
        uiautomator_helper.navigateUp(device);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")),
                LAUNCH_TIMEOUT);

        //day 23
        uiautomator_helper.createNewNoteWithName(device, noteName3);
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/dueDateBox"))
                .click();
        device.findObject(new UiSelector().descriptionContains(day23)).click();
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/done"))
                .click();
        uiautomator_helper.navigateUp(device);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")),
                LAUNCH_TIMEOUT);

        //day 15
        uiautomator_helper.createNewNoteWithName(device, noteName4);
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/dueDateBox"))
                .click();
        device.findObject(new UiSelector().descriptionContains(day15)).click();
        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/done"))
                .click();
        uiautomator_helper.navigateUp(device);

        device.wait(Until.findObject(
                By.res("com.nononsenseapps.notepad:id/fab")),
                LAUNCH_TIMEOUT);

        device.findObject(new UiSelector().resourceId("com.nononsenseapps.notepad:id/menu_sort"))
                .click();
        device.findObject(new UiSelector().text("Order by due date")).click();

        //get the notes (hopefully from top to bottom)
        List<UiObject2> objects = device.findObjects(By.descContains("Item title"));

        assertEquals(4, objects.size());
        for(int i = 0; i < objects.size(); i++){
            assertEquals("Note names do not match", objects.get(i).getText(), expectedNoteOrder[i]);
        }
    }
}
