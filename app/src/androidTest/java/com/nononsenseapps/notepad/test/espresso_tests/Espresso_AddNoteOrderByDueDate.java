package com.nononsenseapps.notepad.test.espresso_tests;

import android.app.DatePickerDialog;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.DatePicker;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_AddNoteOrderByDueDate {

    private String noteName1;
    private String noteName2;
    private String noteName3;
    private String noteName4;

    @Rule
    public ActivityTestRule<ActivityList> myActivityRule =
            new ActivityTestRule<ActivityList>(ActivityList.class);

    @Before
    public void initStrings(){
        noteName1 = "prepare food";
        noteName2 = "take dogs out";
        noteName3 = "water plants";
        noteName4 = "sleep";
    }



    @Test
    public void testAddNotesOrderByDueDate(){

        assertTrue("this test does not work", false);
        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);

//        onView(withText("Due date")).perform(click());
        onView(withId(R.id.dueDateBox)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePickerDialog.class.getName()))).perform(PickerActions.setDate(2016, 6, 27));


    }
}
