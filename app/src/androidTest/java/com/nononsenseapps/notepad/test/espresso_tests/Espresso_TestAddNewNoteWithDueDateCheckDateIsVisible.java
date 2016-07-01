package com.nononsenseapps.notepad.test.espresso_tests;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddNewNoteWithDueDateCheckDateIsVisible extends BaseTestClass{

    private String noteName1;

//    @Rule
//    public ActivityTestRule<ActivityList> myActivityRule =
//            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings() {
        noteName1 = "prepare food";

    }

    @Test
    public void testAddNewNoteWithDueDateCheckDateIsVisible() {

        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);
        onView(withId(R.id.dueDateBox)).perform(click());
        onView(withId(R.id.done)).perform(click());

        Helper.navigateUp();
        onView(withId(R.id.date)).check(matches(isDisplayed()));
    }
}