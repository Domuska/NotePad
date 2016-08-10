package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;


import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.robotium.solo.Solo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Robotium_TestAddNewNoteWithReminderDateAndTime extends ActivityInstrumentationTestCase2<ActivityList> {


    private Solo solo;
    private String noteName1 = "prepare food";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_TestAddNewNoteWithReminderDateAndTime(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        Context context = solo.getCurrentActivity().getApplicationContext();

        //clear app data
        PreferenceManager.
                getDefaultSharedPreferences(
                        context).edit().clear().commit();
        DatabaseHandler.resetDatabase(context);

        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddNewNoteWithReminderDateAndTime(){

        Robotium_Helper.closeDrawer(solo);
        Robotium_Helper.createNoteWithName(solo, noteName1);

        //add notification
        solo.clickOnView(solo.getView(R.id.notificationAdd));

        //add date
        solo.clickOnView(solo.getView(R.id.notificationDate));
        solo.clickOnView(solo.getView(R.id.done));

        //add time
        solo.clickOnView(solo.getView(R.id.notificationTime));
        solo.clickOnView(solo.getView(R.id.done_button));

        Robotium_Helper.navigateUp(solo);

        //open note and check that view with date is visible
        solo.clickOnText(noteName1);
        solo.hideSoftKeyboard();
        solo.getView(R.id.notificationDate);
    }
}
