package com.nononsenseapps.notepad.test.espresso_tests;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;

import org.junit.After;
import org.junit.Rule;

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
