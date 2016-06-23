package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nononsenseapps.notepad.test.espresso_tests.OrientationChangeAction.orientationLandscape;
import static com.nononsenseapps.notepad.test.espresso_tests.OrientationChangeAction.orientationPortrait;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddTaskListAndRotateScreen {

    private String taskListName;

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        taskListName = "a random task list";
    }

    @Test
    public void testAddTaskListAndRotateScreen(){

        onView(withText("Create new"))
                .perform(click());

        onView(withId(com.nononsenseapps.notepad.R.id.titleField)).perform(typeText(taskListName));
        onView(withId(com.nononsenseapps.notepad.R.id.dialog_yes)).perform(click());

        onView(withId(com.nononsenseapps.notepad.R.id.drawer_layout)).perform(DrawerActions.open());

        // rotate to landscape and back to portrait
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());

        //make sure the task list is still visible

        RecyclerViewActions.scrollTo(hasDescendant(withText(taskListName)));
        onView(withText(taskListName)).check(matches(isDisplayed()));

    }
}
