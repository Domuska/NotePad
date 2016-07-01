package com.nononsenseapps.notepad.test.uiautomator_tests;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.Swipe;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

public class uiautomator_helper {

    private static final String NOTEPAD_PACKAGE = "com.nononsenseapps.notepad";
    private static final int LAUNCH_TIMEOUT = 5000;

    public static void startApplication(UiDevice device){
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().
                getLaunchIntentForPackage(NOTEPAD_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(NOTEPAD_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    public static void closeDrawer(UiDevice device) throws Exception{
        UiObject drawer =
                device.findObject(
                        new UiSelector()
                                .resourceId("com.nononsenseapps.notepad:id/navigation_drawer"));
        drawer.swipeLeft(10);
    }

    public static void openDrawer(UiDevice device) throws Exception{
        device.findObject(By.descContains("Open navigation drawer")).click();

        device.wait(Until.hasObject(By.res("com.nononsenseapps.notepad:id/navigation_drawer")),
                LAUNCH_TIMEOUT);
    }

    public static void createNewNoteWithName(UiDevice device, String name) throws Exception{
        device.findObject(
                new UiSelector().resourceId("com.nononsenseapps.notepad:id/fab"))
                .click();

        device.findObject(By.res(NOTEPAD_PACKAGE, "taskText")).setText(name);

    }

    public static void navigateUp(UiDevice device){
        device.findObject(By.descContains("Navigate up")).click();
    }

    public static void createTaskList(UiDevice device, String name) throws Exception{
//        device.findObject(By.text())
        device.findObject(new UiSelector()
                .text("Create new"))
                .click();

        device.findObject(By.res(NOTEPAD_PACKAGE, "titleField")).setText(name);
        device.findObject(By.res(NOTEPAD_PACKAGE, "dialog_yes")).click();
    }


}
