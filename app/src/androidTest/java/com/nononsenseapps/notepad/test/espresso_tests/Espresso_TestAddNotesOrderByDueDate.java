package com.nononsenseapps.notepad.test.espresso_tests;

import android.app.DatePickerDialog;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.datetimepicker.date.SimpleDayPickerView;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddNotesOrderByDueDate {

    private String noteName1;
    private String noteName2;
    private String noteName3;
    private String noteName4;
    private String day04, day05, day15, day23;

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
        noteName2 = "take dogs out";
        noteName3 = "water plants";
        noteName4 = "sleep";

        String currentMonthAndYear = Helper.getMonthAndYear();

        day04 = "04 " + currentMonthAndYear;
        day05 = "05 " + currentMonthAndYear;
        day15 = "15 " + currentMonthAndYear;
        day23 = "23 " + currentMonthAndYear;
    }



    @Test
    public void testAddNotesOrderByDueDate(){


        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);

//        onView(withText("Due date")).perform(click());
        onView(withId(R.id.dueDateBox)).perform(click());

        onData(withContentDescription(day04))
                .inAdapterView(withClassName(endsWith("SimpleDayPickerView")))
                .perform(click());

//        onData(allOf(
////                withClassName(start"SimpleDayPickerView"),
////                isCompletelyDisplayed(),
////                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
////                isDisplayed(),
//                withContentDescription(day04)))
//                .perform(click());



//        onData(withContentDescription(day04)).perform(click());
//        onView(withContentDescription(day04)).perform(click());


    }
}
