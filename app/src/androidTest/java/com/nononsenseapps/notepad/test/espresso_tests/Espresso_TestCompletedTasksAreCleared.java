package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Espresso_Helper;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestCompletedTasksAreCleared extends BaseTestClass{

    String noteName1, noteName2, noteName3, noteName4;

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
        noteName2 = "take dogs out";
        noteName3 = "water plants";
        noteName4 = "sleep";
    }

    @Test
    public void testCompletedTasksAreCleared(){

        Espresso_Helper.closeDrawer();

        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};
        Espresso_Helper.createNotes(noteNames);

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
                        position, MyViewAction.clickChildViewWithId(
                            R.id.checkbox
                ))
        );
    }

    //custom viewAction for clicking a child view of another view,
    //clicks the view that is found with the supplied ID
    private static class MyViewAction{

        public static ViewAction clickChildViewWithId(final int id){
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();

                }
            };
        }
    }


}
