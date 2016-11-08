package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Espresso_Helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestCreateNoteAndDeleteIt extends BaseTestClass{

    private String noteName1;

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
    }

    @Test
    public void testCreateNoteAndDeleteIt() {

        Espresso_Helper.closeDrawer();

        Espresso_Helper.createNoteWithName(noteName1);
        Espresso_Helper.navigateUp();

        onView(withText(noteName1)).perform(click());
        onView(withId(R.id.menu_delete)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        //assert that we're back in the list
        onView(withText("Notes")).check(matches(isDisplayed()));

        //check that the view is not visible
        onView(withText(noteName1)).check(doesNotExist());

    }
}
