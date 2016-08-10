package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.nononsenseapps.notepad.test.Helper;
import com.robotium.solo.Solo;

public class Robotium_TestAddNoteToTaskList extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String taskListName = "a random task list";
    private String noteName1 = "prepare food";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";

    public Robotium_TestAddNoteToTaskList(){
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

    public void testAddNoteToTaskList(){

        Robotium_Helper.createTaskList(solo, taskListName);

        //add the note
        Robotium_Helper.openDrawer(solo);
        solo.clickOnText(taskListName);

        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);

        //make sure that the number of notes for the task list is actually 1
        // not the best way to do it, if there are other fields with "1" in them
        // they will be false positives
        Robotium_Helper.openDrawer(solo);
        boolean noteNumberOneFound = solo.searchText("1", true);
        assertTrue("number one found in navigation drawer", noteNumberOneFound);
    }
}
