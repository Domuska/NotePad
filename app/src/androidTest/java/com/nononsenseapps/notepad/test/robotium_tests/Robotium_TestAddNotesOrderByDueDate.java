package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.nononsenseapps.helpers.Log;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.robotium.solo.Solo;

import java.util.List;

public class Robotium_TestAddNotesOrderByDueDate extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    String[] noteNames = {"prepare food", "take dogs out", "water plants", "sleep"};
    String day3, day8, day15, day21;
    String ORDER_BY_DUE_DATE;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_TestAddNotesOrderByDueDate(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();

//        String currentMonth = Robotium_Helper.getCurrentMonth();
        String dateAndMonth = Robotium_Helper.getDateAndMonth();
        day3 = "03 " + dateAndMonth;
        day8 = "08 " + dateAndMonth;
        day15 = "15 " + dateAndMonth;
        day21 = "21 " + dateAndMonth;
        ORDER_BY_DUE_DATE = getActivity().getResources().getString(R.string.sort_list_due);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    //this test does not work
    public void testAddNotesOrderByDueDate(){

        Robotium_Helper.closeDrawer(solo);


        String[] expectedOrder = new String[]
                {noteNames[2], noteNames[3], noteNames[1], noteNames[0] };

        createNoteWithNameAndDueDate(noteNames[0], day21);
        createNoteWithNameAndDueDate(noteNames[1], day15);
        createNoteWithNameAndDueDate(noteNames[2], day3);
        createNoteWithNameAndDueDate(noteNames[3], day8);

        solo.clickOnView(solo.getView(R.id.menu_sort));
        solo.clickOnText(ORDER_BY_DUE_DATE);

        View recyclerView = solo.getView(android.R.id.list);
        List<View> views = solo.getViews(recyclerView);

    }

    private void createNoteWithNameAndDueDate(String noteName, String dueDate) {
        Robotium_Helper.createNoteWithName(solo, noteName);
        solo.clickOnView(solo.getView(R.id.dueDateBox));


        List<View> views = solo.getViews();
        for(int i = 0; i < views.size()-1; i++) {

            if(views.get(i).getContentDescription() != null &&
                    views.get(i).getContentDescription().equals(dueDate)) {
                solo.clickOnView(views.get(i));
            }
            else
                fail();
        }

//        solo.setDatePicker(0, 2016, 06, 12);
//        DatePicker datePicker = (DatePicker) solo.getView(android.R.id.content);
//        datePicker.updateDate(2016, 6, 13);
//        List<View> views = solo.getViews();


        //search for the view with content description and click it
//        for(int i = 0; i < views.size(); i++){
//            View v = views.get(i);
//            if(v.getContentDescription() != null &&
//                    v.getContentDescription().toString().equals(dueDate)){
//                solo.clickOnView(v);
//            }
//        }
        solo.clickOnView(solo.getView(R.id.done));
        solo.waitForDialogToClose();
        Robotium_Helper.navigateUp(solo);

    }


//    private void addNoteAndDueDate(String noteName) {
//        Robotium_Helper.createNoteWithName(solo, noteName);
//        solo.clickOnView(solo.getView(R.id.dueDateBox));
//        solo.clickOnView(solo.getView(R.id.done));
//
//        Robotium_Helper.navigateUp(solo);
//    }

}
