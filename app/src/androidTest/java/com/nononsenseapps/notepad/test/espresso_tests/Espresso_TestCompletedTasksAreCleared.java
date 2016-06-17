package com.nononsenseapps.notepad.test.espresso_tests;

import android.content.res.Resources;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Helper;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestCompletedTasksAreCleared {

    String noteName1, noteName2, noteName3, noteName4;

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
        noteName2 = "take dogs out";
        noteName3 = "water plants";
        noteName4 = "sleep";
    }

    @Test
    public void testCompletedTasksAreCleared(){

        Helper.closeDrawer();

        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};
        Helper.createNotes(noteNames);

        clickCheckBoxAt(1);
        clickCheckBoxAt(3);

        //clear notes
        openContextualActionModeOverflowMenu();
        onView(withText("Clear completed")).perform(click());
        onView(withText("OK")).perform(click());

        //check that the notes do not exist any more
        onView(withText(noteNames[0]))
                .check(doesNotExist());
        onView(withText(noteNames[2]))
                .check(doesNotExist());
    }

    private void clickCheckBoxAt(int position) {
        onView(withId(android.R.id.list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        position, Helper.MyViewAction.clickChildViewWithId(
                            R.id.checkbox
                ))
        );
    }


}
