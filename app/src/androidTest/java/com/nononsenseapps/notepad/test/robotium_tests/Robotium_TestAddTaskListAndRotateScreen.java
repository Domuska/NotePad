package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.robotium.solo.Solo;

public class Robotium_TestAddTaskListAndRotateScreen extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String taskListName = "a random task list";

    public Robotium_TestAddTaskListAndRotateScreen(){
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

    public void testAddTaskListAndRotateScreen(){
        Robotium_Helper.createTaskList(solo, taskListName);

        Robotium_Helper.openDrawer(solo);
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.setActivityOrientation(Solo.PORTRAIT);

        solo.searchText(taskListName);
    }
}
