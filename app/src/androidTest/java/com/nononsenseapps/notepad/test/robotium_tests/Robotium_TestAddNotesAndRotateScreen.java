package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.robotium.solo.Solo;

public class Robotium_TestAddNotesAndRotateScreen extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String noteName1 = "prepare food";
    private String noteName2 = "take dogs out";
    private String noteName3 = "water plants";
    private String noteName4 = "sleep";

    public Robotium_TestAddNotesAndRotateScreen(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    public void tearDown() throws Exception{
        Context context = solo.getCurrentActivity().getApplicationContext();

        //clear app data
        PreferenceManager.
                getDefaultSharedPreferences(
                        context).edit().clear().commit();
        DatabaseHandler.resetDatabase(context);

        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddNotesAndRotateScreen(){
        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};

        Robotium_Helper.closeDrawer(solo);
        Robotium_Helper.createNotes(solo, noteNames);

        //rotate screen
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.setActivityOrientation(Solo.PORTRAIT);

        solo.searchText(noteNames[0]);
        solo.searchText(noteNames[1]);
        solo.searchText(noteNames[2]);
        solo.searchText(noteNames[3]);
    }
}
