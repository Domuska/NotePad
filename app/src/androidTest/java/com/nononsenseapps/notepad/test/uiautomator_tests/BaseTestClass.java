package com.nononsenseapps.notepad.test.uiautomator_tests;


import android.app.UiAutomation;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.view.accessibility.AccessibilityWindowInfo;

import com.nononsenseapps.notepad.database.DatabaseHandler;

import org.junit.After;
import org.junit.Before;

public class BaseTestClass {

    public static final String NOTEPAD_PACKAGE = "com.nononsenseapps.notepad";
    public static final int LAUNCH_TIMEOUT = 5000;
    protected UiDevice device;
    public static final int GENERAL_TIMEOUT = 5000;

    @Before
    public final void setUpBaseTestClass(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiautomator_helper.startApplication(device);
    }

    @After
    public void tearDown() throws Exception{

        //clear the app's preferences and database
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit().clear().commit();
        DatabaseHandler.resetDatabase(context);
    }

    protected boolean isKeyboardOpened(){
        UiAutomation uiAutomation = InstrumentationRegistry.getInstrumentation().getUiAutomation();

        for(AccessibilityWindowInfo window: uiAutomation.getWindows()){
            if(window.getType()== AccessibilityWindowInfo.TYPE_INPUT_METHOD){
                return true;
            }
        }
        return false;
    }
}
