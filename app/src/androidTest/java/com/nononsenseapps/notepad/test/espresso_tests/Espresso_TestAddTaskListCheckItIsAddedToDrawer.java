package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.test.Espresso_Helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddTaskListCheckItIsAddedToDrawer extends BaseTestClass{


    private String taskListName;

    @Before
    public void initStrings(){
        taskListName = "a random task list";
    }

    @Test
    public void testAddTaskListCheckItIsAddedToDrawer(){

        Espresso_Helper.createTaskList(taskListName);
        Espresso_Helper.openDrawer();

        //check that the new note is found and has the correct text
        onView(withText(taskListName)).check(matches(isDisplayed()));
    }

}

