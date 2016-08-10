package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddNewNoteWithReminderDateAndTime extends BaseTestClass{

    private String noteName1;

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
    }

    @Test
    public void testAddNewNoteWithReminderDateAndTime(){

        Helper.closeDrawer();
        Helper.createNoteWithName(noteName1);

        //add reminder
        onView(withId(com.nononsenseapps.notepad.R.id.notificationAdd)).perform(click());

        //add date
        onView(withId(com.nononsenseapps.notepad.R.id.notificationDate)).perform(click());
        onView(withId(com.nononsenseapps.notepad.R.id.done)).perform(click());

        //add time
        onView(withId(com.nononsenseapps.notepad.R.id.notificationTime)).perform(click());
        onView(withId(com.nononsenseapps.notepad.R.id.done_button)).perform(click());

        Helper.navigateUp();

        //check that the date field is visible
        onView(withText(noteName1)).perform(click());
        onView(withId(R.id.notificationDate)).check(matches(isDisplayed()));
    }
}
