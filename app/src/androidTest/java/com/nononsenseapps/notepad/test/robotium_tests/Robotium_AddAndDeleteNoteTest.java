package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.robotium.solo.Solo;


public class Robotium_AddAndDeleteNoteTest extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String noteName1 = "prepare food";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_AddAndDeleteNoteTest(){
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


    public void testCreateNoteAndDeleteIt() throws Exception{

        solo.waitForActivity("ActivityList", 1500);

        Robotium_Helper.closeDrawer(solo);
        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);

        solo.clickOnText(noteName1);

        solo.clickOnView(solo.getView(R.id.menu_delete));
        solo.clickOnView(solo.getView(android.R.id.button1));

        boolean noteFound = solo.searchText(noteName1);
        assertFalse("note found", noteFound);
    }

}
