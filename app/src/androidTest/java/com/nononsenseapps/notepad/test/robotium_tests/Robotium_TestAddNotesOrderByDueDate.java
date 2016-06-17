package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.DatePicker;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.robotium.solo.Solo;

import org.junit.Ignore;

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

        String currentMonth = Robotium_Helper.getCurrentMonth();
        day3 = "03 " + currentMonth + " 2016";
        day8 = "08 " + currentMonth + " 2016";
        day15 = "15 " + currentMonth + " 2016";
        day21 = "21 " + currentMonth + " 2016";
        ORDER_BY_DUE_DATE = getActivity().getResources().getString(R.string.sort_list_due);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    //this test does not work
    public void IGNORE_testAddNotesOrderByDueDate(){

        Robotium_Helper.closeDrawer(solo);

        String[] expectedOrder = new String[]
                {noteNames[2], noteNames[3], noteNames[1], noteNames[0] };

        createNoteWithNameAndDueDate(noteNames[0], day21);
//        createNoteWithNameAndDueDate(noteNames[1], day15);
//        createNoteWithNameAndDueDate(noteNames[2], day3);
//        createNoteWithNameAndDueDate(noteNames[3], day8);

        solo.clickOnView(solo.getView(R.id.menu_sort));
        solo.clickOnText(ORDER_BY_DUE_DATE);

        View recyclerView = solo.getView(android.R.id.list);
        List<View> views = solo.getViews(recyclerView);


        assertEquals(255, day8);
        assertEquals(255, views.size());


    }

    private void createNoteWithNameAndDueDate(String noteName, String dueDate) {
        Robotium_Helper.createNoteWithName(solo, noteName);
        solo.clickOnView(solo.getView(R.id.dueDateBox));

//        solo.setDatePicker(0, 2016, 06, 12);
        DatePicker datePicker = (DatePicker) solo.getView(android.R.id.content);
        datePicker.updateDate(2016, 6, 13);
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
