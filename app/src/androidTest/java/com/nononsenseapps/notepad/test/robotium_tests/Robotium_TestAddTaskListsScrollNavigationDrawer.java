package com.nononsenseapps.notepad.test.robotium_tests;

import android.content.Context;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.robotium.solo.Solo;

public class Robotium_TestAddTaskListsScrollNavigationDrawer extends ActivityInstrumentationTestCase2<ActivityList> {

    private Solo solo;
    private String[] taskListNames = {"Lorem", "ipsum ", "dolor ", "sit ", "amet", "consectetur ",
            "adipiscing ", "elit", "sed ", "do ", "eiusmod ", "tempor ", "incididunt ",
            "ut ", "labore "};
    private String SETTINGS_TEXT, SETTINGS_APPEARANCE_TEXT;

    public Robotium_TestAddTaskListsScrollNavigationDrawer(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
        SETTINGS_TEXT = getActivity().getString(R.string.menu_preferences);
        SETTINGS_APPEARANCE_TEXT = getActivity().getString(R.string.settings_cat_appearance);
    }

    @Override
    public void tearDown() throws Exception {
        Context context = solo.getCurrentActivity().getApplicationContext();

        //clear app data
        PreferenceManager.
                getDefaultSharedPreferences(
                        context).edit().clear().commit();
        DatabaseHandler.resetDatabase(context);

        solo.goBack();
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddTaskListsScrollNavigationDrawer(){

        //add the task lists
        for(String name : taskListNames){
            Robotium_Helper.createTaskList(solo, name);
            Robotium_Helper.openDrawer(solo);
        }

        //scroll the nav drawer recycler view
        solo.scrollRecyclerViewToBottom(1);

        solo.clickOnText(SETTINGS_TEXT);
        solo.searchText(SETTINGS_APPEARANCE_TEXT);
    }
}
