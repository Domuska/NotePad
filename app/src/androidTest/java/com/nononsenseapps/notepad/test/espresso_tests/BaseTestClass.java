package com.nononsenseapps.notepad.test.espresso_tests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BaseTestClass {

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);


    @After
    public void tearDown(){
        Context context = myActivityRule.getActivity().getApplicationContext();

        //clear the app's data as the test is finishing
        PreferenceManager.
                getDefaultSharedPreferences(
                        context).edit().clear().commit();
        DatabaseHandler.resetDatabase(context);

        //start the original activity, make sure we get back from settings and such
        Intent i = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);

    }



}
