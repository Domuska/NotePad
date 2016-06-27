package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
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
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddTaskListsScrollNavigationDrawer(){

        for(String name : taskListNames){
            Robotium_Helper.createTaskList(solo, name);
            Robotium_Helper.openDrawer(solo);
        }

        solo.scrollRecyclerViewToBottom(1);
        solo.clickOnText(SETTINGS_TEXT);
        solo.searchText(SETTINGS_APPEARANCE_TEXT);
    }
}
