package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;
import com.robotium.solo.Solo;

public class Robotium_AddNoteToTaskList extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String taskListName = "a random task list";
    private String noteName1 = "prepare food";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_AddNoteToTaskList(){
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

    public void testAddNoteToTaskList(){

        solo.clickOnText("Create new");
        solo.enterText(
                (EditText)solo.getView(R.id.titleField),
                taskListName
        );

        solo.clickOnView(solo.getView(R.id.dialog_yes));

        Robotium_Helper.openDrawer(solo);

        solo.clickOnText(taskListName);

        Robotium_Helper.createNoteWithName(solo, noteName1);
        Helper.navigateUp();

        boolean noteFound = solo.searchText(noteName1);
        assertTrue("note found", noteFound);

        Robotium_Helper.openDrawer(solo);

        boolean noteNumberOneFound = solo.searchText("1", true);
        assertTrue("number one found in navigation drawer", noteNumberOneFound);
    }
}
