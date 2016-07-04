package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddBigNumberOfNotesScrollDownAndDeleteOne extends BaseTestClass{

    private String[] noteNameList =
            {"prepare food", "take dogs out", "water plants", "sleep",
            "go for a jog", "do some work", "play with the dog",
            "work out", "do weird stuff", "read a book", "drink water",
            "write a book", "proofread the book", "publish the book",
            "ponder life", "build a house", "repair the house", "call contractor",
            "write another book", "scrap the book project", "start a blog",
            "  ", "     "
            };


    @Test
    public void testAddBigNumberOfNotesScrollDownAndDeleteOne(){

        Helper.closeDrawer();
        //create the notes
        Helper.createNotes(noteNameList);

        onView(withId(android.R.id.list)).perform(RecyclerViewActions.actionOnItem(
                hasDescendant(withText(noteNameList[0])), click()
        ));

        //delete the last note
        onView(withId(R.id.menu_delete)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        //assert the note is not visible
        //todo finish this. How to do it?
        // http://alexander-thiele.blogspot.fi/2016/01/espresso-ui-tests-and-recyclerview.html
//        onView(withId(android.R.id.list)).perform(RecyclerViewActions.scrollTo(
//                withText(noteNameList[0])
//        ));

    }
}
