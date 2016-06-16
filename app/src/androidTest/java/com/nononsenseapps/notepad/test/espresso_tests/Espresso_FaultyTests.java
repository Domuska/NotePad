package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_FaultyTests {

    private String noteName1;
    private String createNewText;


    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
        createNewText = myActivityRule.getActivity().getResources().getString(R.string.menu_createnew);
    }

    @Test
    public void testAddNewNoteSearchForFaultyNoteName(){

        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);
        Helper.navigateUp();

        onView(withText(noteName1)).check(matches(withText(noteName1 + "asdf")));
        assertFalse("should have failed before this", true);
    }

    @Test
    public void testSearchForElementWithTextShouldFailOnView(){
        Helper.closeDrawer();
        onView(withText(createNewText)).perform(click());
        assertFalse("should have failed before this", true);
    }

    @Test
    public void testSearchForElementWithIDShouldFailOnView(){
        Helper.closeDrawer();
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        assertFalse("should have failed before this", true);
    }

    @Test
    public void testSearchForElementWithIDShouldFailOnCompileTime(){
//        onView(withId(R.id.faulty_id)).perform(click());
    }

    @Test
    public void testSearchForElementWithAmbiguousIdentifier(){
        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);
        Helper.navigateUp();
        Helper.createNoteWithName(noteName1);
        Helper.navigateUp();

        onView(withText(noteName1)).perform(click());
        assertFalse("should have failed before this", true);
    }

}
