package com.nononsenseapps.notepad.test.espresso_faulty_tests;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Espresso_Helper;
import com.nononsenseapps.notepad.test.espresso_tests.BaseTestClass;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_FaultyTests extends BaseTestClass {

    private String noteName1;
    private String createNewText;

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
        createNewText = myActivityRule.getActivity().getResources().getString(R.string.menu_createnew);
    }

    @Test
    @Ignore
    public void testAddNewNoteSearchForFaultyNoteName(){

        Espresso_Helper.closeDrawer();

        Espresso_Helper.createNoteWithName(noteName1);
        Espresso_Helper.navigateUp();

        onView(withText(noteName1)).check(matches(withText(noteName1 + "asdf")));
        assertFalse("should have failed before this", true);
    }

    @Test
    @Ignore
    public void testSearchForElementWithTextShouldFailOnView(){
        Espresso_Helper.closeDrawer();
        onView(withText(createNewText)).perform(click());
        assertFalse("should have failed before this", true);
    }

    @Test
    @Ignore
    public void testSearchForElementWithIDShouldFailOnView(){
        Espresso_Helper.closeDrawer();
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.fab)).perform(click());
        assertFalse("should have failed before this", true);
    }

    @Test
    @Ignore
    public void testSearchForElementWithFaultyID(){
//        onView(withId(R.id.faulty_id)).perform(click());
        fail();
    }

    @Test
    @Ignore
    public void testSearchForElementWithAmbiguousIdentifier(){
        Espresso_Helper.closeDrawer();

        Espresso_Helper.createNoteWithName(noteName1);
        Espresso_Helper.navigateUp();
        Espresso_Helper.createNoteWithName(noteName1);
        Espresso_Helper.navigateUp();

        onView(withText(noteName1)).perform(click());
        assertFalse("should have failed before this", true);
    }

}
