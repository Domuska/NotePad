package com.nononsenseapps.notepad.test.espresso_tests;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.datetimepicker.date.SimpleDayPickerView;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_TestAddNotesOrderByDueDate extends BaseTestClass{

    private String noteName1;
    private String noteName2;
    private String noteName3;
    private String noteName4;
    private String day04, day05, day15, day23;

//    @Rule
//    public ActivityTestRule<ActivityList> myActivityRule =
//            new ActivityTestRule<ActivityList>(ActivityList.class);

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
    @Ignore
    public void testAddNotesOrderByDueDate(){

        fail("Automating the datetimepicker not possible, failing test");
        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);

//        onView(withText("Due date")).perform(click());
        onView(withId(R.id.dueDateBox)).perform(click());
        Activity activity = myActivityRule.getActivity();

//        onView(withContentDescription(day04))
//                .inRoot(isPlatformPopup())
//                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
//                .perform(click());

        onData(anything())
                .atPosition(3)
                .inAdapterView(withClassName(CoreMatchers.endsWith("SimpleDayPickerView")))
                .perform(click());
//        onData(hasToString(day04))
//                .inAdapterView(withClassName(endsWith("SimpleDayPickerView")))
//                .perform(click());

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
