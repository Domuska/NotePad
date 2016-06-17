package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.robotium.solo.Solo;

public class Robotium_TestAddTaskListCheckItIsAddedToDrawer extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String taskListName = "a random task list";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_TestAddTaskListCheckItIsAddedToDrawer(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddTaskListCheckItIsAddedToDrawer(){

        solo.clickOnText("Create new");

        solo.enterText(
                (EditText)solo.getView(R.id.titleField),
                taskListName
        );

        solo.clickOnView(solo.getView(R.id.dialog_yes));
        Robotium_Helper.openDrawer(solo);

        boolean taskListFound = solo.searchText(taskListName, true);
        assertTrue("task list found", taskListFound);
    }
}
