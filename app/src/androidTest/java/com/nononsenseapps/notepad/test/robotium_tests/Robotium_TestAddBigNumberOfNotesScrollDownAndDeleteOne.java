package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.nononsenseapps.notepad.test.Helper;
import com.robotium.solo.Solo;

import org.junit.Ignore;

public class Robotium_TestAddBigNumberOfNotesScrollDownAndDeleteOne extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String[] noteNameList =
            {"prepare food", "take dogs out", "water plants", "sleep",
                    "go for a jog", "do some work", "play with the dog",
                    "work out", "do weird stuff", "read a book", "drink water",
                    "write a book", "proofread the book", "publish the book",
                    "ponder life", "build a house", "repair the house", "call contractor",
                    "write another book", "scrap the book project", "start a blog",
                    "  ", "     "
            };


    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_TestAddBigNumberOfNotesScrollDownAndDeleteOne(){
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

    public void estAddBigNumberOfNotesScrollDownAndDeleteOne(){

        Robotium_Helper.closeDrawer(solo);

        Robotium_Helper.createNotes(solo, noteNameList);

        boolean noteFound = false;
        boolean canBeScrolled = true;
        while(!noteFound && canBeScrolled){

            if(solo.searchText(noteNameList[0])) {
                noteFound = true;
            }
            canBeScrolled = solo.scrollDownRecyclerView(0);
        }
        assertTrue("note found in the list", noteFound);
    }
}
