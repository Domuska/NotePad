package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.robotium.solo.Solo;

import java.util.List;

public class Robotium_AddNotesOrderByDate extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    String[] noteNames = {"prepare food", "take dogs out", "water plants", "sleep"};

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_AddNotesOrderByDate(){
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

    public void testAddNotesOrderByDueDate(){

        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNoteWithName(solo, noteNames[0]);
        solo.clickOnView(solo.getView(R.id.dueDateBox));

        solo.getView(R.id.animator);

        List<View> views = solo.getViews();
        String currentDate = Robotium_Helper.getDateAndMonth();

        //todo elä käytä oikeita päivämääriä vaan käytä joka kuusta vaikka 15. 16. 17. 18. pvä

        for(int i = 0; i < views.size(); i++){
            View v = views.get(i);
            if(v.getContentDescription() != null &&
                    v.getContentDescription().equals(currentDate)){
                solo.clickOnView(v);
            }
        }
        solo.clickOnView(solo.getView(R.id.done));
        Robotium_Helper.navigateUp(solo);


//        addNoteAndDueDate(noteNames[0]);
//        addNoteAndDueDate(noteNames[1]);
//        addNoteAndDueDate(noteNames[2]);
//        addNoteAndDueDate(noteNames[3]);




    }

    private void addNoteAndDueDate(String noteName) {
        Robotium_Helper.createNoteWithName(solo, noteName);
        solo.clickOnView(solo.getView(R.id.dueDateBox));
        solo.clickOnView(solo.getView(R.id.done));

        Robotium_Helper.navigateUp(solo);
    }

}
