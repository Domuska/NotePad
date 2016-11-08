package com.nononsenseapps.notepad.test.espresso_tests;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Espresso_Helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddTaskListsScrollNavigationDrawer extends BaseTestClass{

    String[] taskListNames = {"Lorem", "ipsum ", "dolor ", "sit ", "amet", "consectetur ",
            "adipiscing ", "elit", "sed ", "do ", "eiusmod ", "tempor ", "incididunt ",
            "ut ", "labore "};

    String SETTINGS_TEXT, SETTINGS_APPEARANCE_TEXT;

    @Before
    public void initStrings(){
        SETTINGS_TEXT =
                myActivityRule.getActivity().getString(R.string.menu_preferences);
        SETTINGS_APPEARANCE_TEXT =
                myActivityRule.getActivity().getString(R.string.settings_cat_appearance);
    }

    @Test
    public void testAddTaskListsScrollNavigationDrawer(){

        for(String name : taskListNames){
            Espresso_Helper.createTaskList(name);
            Espresso_Helper.openDrawer();
        }

        onView(withId(R.id.navigation_drawer)).perform(RecyclerViewActions.actionOnItem(
                hasDescendant(withText(SETTINGS_TEXT)), click()
        ));

        onView(withText(SETTINGS_APPEARANCE_TEXT)).check(matches(isDisplayed()));
    }
}
