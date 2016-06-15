package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.espresso.contrib.DrawerActions;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_AddNoteToTaskList {

    private String taskListName, noteName1;

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        taskListName = "a random task list";
        noteName1 = "prepare food";
    }

    @Test
    public void testAddNoteToTaskList(){

        Helper.openDrawer();

        //create the task list
        onView(withText("Create new"))
                .perform(click());

        onView(withId(com.nononsenseapps.notepad.R.id.titleField)).perform(typeText(taskListName));
        onView(withId(com.nononsenseapps.notepad.R.id.dialog_yes)).perform(click());
        onView(withId(com.nononsenseapps.notepad.R.id.drawer_layout)).perform(DrawerActions.open());

        //add the note
        Helper.openDrawer();

        onView(withText(taskListName)).perform(click());
        Helper.createNoteWithName(noteName1);
        Helper.navigateUp();

        //make sure that the number of notes for the task list is actually 1
        Helper.openDrawer();
        onView(allOf(withText(taskListName), hasSibling(withText("1")))).check(matches(withText(taskListName)));

    }
}
