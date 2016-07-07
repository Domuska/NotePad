package com.nononsenseapps.notepad.test.uiautomator_tests;


import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.test.InstrumentationRegistry;
import android.view.accessibility.AccessibilityWindowInfo;

import com.nononsenseapps.notepad.database.DatabaseHandler;

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

        //clear the app's preferences and database
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit().clear().commit();
        DatabaseHandler.resetDatabase(context);

        //grab screenshot when the test is ending
//        InstrumentationRegistry.getInstrumentation().getUiAutomation()
//                .executeShellCommand("screencap /storage/emulated/0/Download/screen.png")
//                .close();

//        InstrumentationRegistry.getInstrumentation().getUiAutomation()
//                .executeShellCommand("pm clear com.nononsenseapps.notepad")
//        .close();

//        InstrumentationRegistry.getInstrumentation().getUiAutomation()
//                .executeShellCommand("am start -W -n com.nononsenseapps.notepad/.activities.ActivityList")
//                .close();


//        InstrumentationRegistry.getInstrumentation().finish(-1, null);


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
