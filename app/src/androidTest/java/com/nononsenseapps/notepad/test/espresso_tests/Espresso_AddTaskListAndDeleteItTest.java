package com.nononsenseapps.notepad.test.espresso_tests;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_AddTaskListAndDeleteItTest {

    private String taskListName;

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        taskListName = "a random task list";

    }

    //hello world

    @Test
    public void testCreateTaskListAndDeleteIt(){

        onView(withText("Create new")).perform(click());
        onView(withId(R.id.titleField)).perform(typeText(taskListName));
        onView(withId(R.id.dialog_yes)).perform(click());

        Helper.openDrawer();

        onView(withText(taskListName))
                .check(matches(withText(taskListName)));

        onView(withText(taskListName))
                .perform(longClick());

        onView(withId(R.id.deleteButton))
                .perform(click());

        onView(withText("OK"))
                .perform(click());

        onView(withText(taskListName))
                .check(doesNotExist());
    }

}
