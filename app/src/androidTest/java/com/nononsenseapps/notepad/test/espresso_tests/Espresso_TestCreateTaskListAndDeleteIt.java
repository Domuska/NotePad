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
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestCreateTaskListAndDeleteIt extends BaseTestClass{

    private String taskListName;

    @Before
    public void initStrings(){
        taskListName = "a random task list";

    }

    @Test
    public void testCreateTaskListAndDeleteIt(){

        Espresso_Helper.createTaskList(taskListName);

        Espresso_Helper.openDrawer();

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
