package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.robotium.solo.Solo;

public class Robotium_AddNoteAddDueDate extends ActivityInstrumentationTestCase2<ActivityList> {
    private Solo solo;
    private String noteName1 = "prepare food";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_AddNoteAddDueDate(){
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

    public void testAddNewNoteWithDueDateCheckDateIsVisible(){

        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNoteWithName(solo, noteName1);
        solo.clickOnView(solo.getView(R.id.dueDateBox));
        solo.clickOnView(solo.getView(R.id.done));

        Robotium_Helper.navigateUp(solo);


        //we assert that it is good enough to know that the visibility is set to VISIBLE,
        //we cannot however be sure that the view is actually visible (not obscured by something)
        View dueDateView = solo.getView(R.id.date);

        assertEquals("Due date view is visible",
                dueDateView.getVisibility(),
                View.VISIBLE);
    }

}
