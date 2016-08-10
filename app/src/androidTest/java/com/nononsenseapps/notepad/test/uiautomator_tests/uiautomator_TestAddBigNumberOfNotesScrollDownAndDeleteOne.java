package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

public class uiautomator_TestAddBigNumberOfNotesScrollDownAndDeleteOne extends BaseTestClass{

    private String noteName1 = "prepare food";
    private String noteName2 = "take dogs out";
    private String noteName3 = "water plants";
    private String noteName4 = "sleep";
    private String[] noteNameList = {noteName1, noteName2, noteName3, noteName4,
            "go for a jog", "do some work", "play with the dog",
            "work out", "do weird stuff", "read a book", "drink water",
            "write a book", "proofread the book", "publish the book",
            "ponder life", "build a house", "repair the house", "call contractor",
            "write another book", "scrap the book project", "start a blog",
            "  ", "     "};

    @Test
    public void testAddBigNumberOfNotesScrollDownAndDeleteOne() throws Exception{

        uiautomator_helper.closeDrawer(device);
        //add notes
        uiautomator_helper.createNotes(device, noteNameList);

        //scroll to last one
        UiScrollable recyclerView = new UiScrollable(
                new UiSelector().resourceId("android:id/list"));
        recyclerView.scrollTextIntoView(noteNameList[0]);

        //delete the note
        device.findObject(By.text(noteNameList[0])).click();
        device.findObject(By.res("com.nononsenseapps.notepad:id/menu_delete")).click();
        device.findObject(By.res("android:id/button1")).click();

        //assert that the note is no more visible
        recyclerView = new UiScrollable(
                new UiSelector().resourceId("android:id/list"));
        boolean note1TitleFound = recyclerView.scrollTextIntoView(noteName1);

        assertFalse("Note with name " + noteName1 + " should not be visible", note1TitleFound);
    }

}
