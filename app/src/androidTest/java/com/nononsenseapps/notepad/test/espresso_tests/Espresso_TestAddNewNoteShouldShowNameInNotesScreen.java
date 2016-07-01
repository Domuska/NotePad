package com.nononsenseapps.notepad.test.espresso_tests;


import android.content.Context;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.database.DatabaseHandler;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddNewNoteShouldShowNameInNotesScreen extends BaseTestClass{

    private String noteName1;


//    @Rule
//    public ActivityTestRule<ActivityList> myActivityRule =
//            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
    }

//    @After
//    public void tearDown(){
//        Context context = myActivityRule.getActivity().getApplicationContext();
//
//        PreferenceManager.
//                getDefaultSharedPreferences(
//                        context).edit().clear().commit();
//
//        context.deleteDatabase(DatabaseHandler.DATABASE_NAME);
//
//        DatabaseHandler.getInstance(context).getReadableDatabase();
//    }

    @Test
    public void testAddNewNoteShouldShowNameInNotesScreen(){

        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);
        Helper.navigateUp();

        //onView(withText(noteName1)).check(matches(withText(noteName1)));
        onView(withText(noteName1)).check(matches(isDisplayed()));
    }

}
