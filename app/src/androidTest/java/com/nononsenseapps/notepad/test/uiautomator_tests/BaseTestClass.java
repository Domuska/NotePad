package com.nononsenseapps.notepad.test.uiautomator_tests;


import android.app.Instrumentation;
import android.app.UiAutomation;
import android.os.ParcelFileDescriptor;
import android.provider.SyncStateContract;
import android.support.test.InstrumentationRegistry;
import android.view.accessibility.AccessibilityWindowInfo;

import org.junit.After;

public class BaseTestClass {

    public static final String NOTEPAD_PACKAGE = "com.nononsenseapps.notepad";
    public static final int LAUNCH_TIMEOUT = 5000;

    @After
    public void tearDown() throws Exception{
        /*
        //clear the app's data as the test is finishing
        PreferenceManager.
                getDefaultSharedPreferences(
                        context).edit().clear().commit();
        DatabaseHandler.resetDatabase(context);
         */

        // does not work too well at the moment,
        // Test running failed: Instrumentation run failed due to 'Process crashed.'
        // comes up often
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("pm clear com.nononsenseapps.notepad")
                .close();
//                .executeShellCommand("pm clear " + NOTEPAD_PACKAGE);
//                .close();

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
