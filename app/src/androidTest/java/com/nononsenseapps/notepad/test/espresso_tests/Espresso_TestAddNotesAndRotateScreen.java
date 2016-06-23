package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nononsenseapps.notepad.test.espresso_tests.OrientationChangeAction.orientationLandscape;
import static com.nononsenseapps.notepad.test.espresso_tests.OrientationChangeAction.orientationPortrait;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddNotesAndRotateScreen {

    String noteName1, noteName2, noteName3, noteName4;

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
        noteName2 = "take dogs out";
        noteName3 = "water plants";
        noteName4 = "sleep";
    }

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Test
    public void testAddNotesAndRotateScreen(){


        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};

        Helper.closeDrawer();
        Helper.createNotes(noteNames);

        // rotate screen
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());

        //check that textviews still show up
        onView(withText(noteName1)).check(matches(isDisplayed()));
        onView(withText(noteName2)).check(matches(isDisplayed()));
        onView(withText(noteName3)).check(matches(isDisplayed()));
        onView(withText(noteName4)).check(matches(isDisplayed()));

    }
}


