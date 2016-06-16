package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_AddTaskListTest {


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
    public void testAddTaskListCheckItIsAddedToDrawer(){

        Helper.closeDrawer();

        onView(withText("Create new"))
                .perform(click());

        onView(withId(R.id.titleField)).perform(typeText(taskListName));
        onView(withId(R.id.dialog_yes)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        //check that the new note is found and has the correct text
        onView(withText(taskListName)).check(matches(withText(taskListName)));

    }




}

