package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;
import com.robotium.solo.Solo;

public class Robotium_Faulty_Tests extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String noteName1 = "prepare food";
    private String createNewText;

    public Robotium_Faulty_Tests(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
        createNewText = getActivity().getString(R.string.menu_createnew);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddNewNoteSearchForFaultyNoteName(){

        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);


        solo.clickOnView(solo.getText(noteName1 + "asdf"));
        assertFalse("should have failed before this", true);
    }

    public void testSearchForElementWithTextShouldFailOnView(){

        Robotium_Helper.closeDrawer(solo);
        solo.clickOnText(createNewText);
        assertFalse("should have failed before this", true);
    }

    public void testSearchForElementWithIDShouldFailOnView(){
        Robotium_Helper.closeDrawer(solo);

        solo.clickOnView(solo.getView(R.id.fab));
        solo.clickOnView(solo.getView(R.id.fab));

        assertFalse("should have failed before this", true);
    }

    public void testSearchForElementWithIDShouldFailOnCompileTime(){
//        solo.clickOnView(solo.getView(R.id.faulty_id));
    }

    public void testSearchForElementWithAmbiguousIdentifier(){
        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);
        Robotium_Helper.createNoteWithName(solo, noteName1);
        Robotium_Helper.navigateUp(solo);

        solo.clickOnText(noteName1);
        assertFalse("should have failed before this", true);
    }
}
