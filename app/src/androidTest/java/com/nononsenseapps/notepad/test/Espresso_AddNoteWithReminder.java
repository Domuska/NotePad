package com.nononsenseapps.notepad.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.*;
import com.nononsenseapps.notepad.activities.ActivityList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_AddNoteWithReminder {

    private String noteName1;

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
    }

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Test
    public void testAddNewNoteWithReminderDateAndTime(){

        Helper.closeDrawer();
        Helper.createNoteWithName(noteName1);

        onView(withId(com.nononsenseapps.notepad.R.id.notificationAdd)).perform(click());

        onView(withId(com.nononsenseapps.notepad.R.id.notificationDate)).perform(click());
        onView(withId(com.nononsenseapps.notepad.R.id.done)).perform(click());

        onView(withId(com.nononsenseapps.notepad.R.id.notificationTime)).perform(click());
        onView(withId(com.nononsenseapps.notepad.R.id.done_button)).perform(click());

        onView(withContentDescription(Helper.NAVIGATE_UP_TEXT)).perform(click());

        onView(withText(noteName1)).check(matches(withText(noteName1)));
    }
}
