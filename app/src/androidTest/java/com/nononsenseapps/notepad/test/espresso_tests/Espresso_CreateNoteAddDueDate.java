package com.nononsenseapps.notepad.test.espresso_tests;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.suitebuilder.annotation.LargeTest;

import com.nononsenseapps.notepad.R;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_CreateNoteAddDueDate {

    private String noteName1, noteName2, noteName3, noteName4;

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

    @After
    public void stopActivity(){
        ActivityFinisher.finishOpenActivities();
    }

    @Test
    public void testAddNewNoteWithDueDateCheckDateIsVisible(){

        Helper.closeDrawer();

        Helper.createNoteWithName(noteName1);
        onView(withId(R.id.dueDateBox)).perform(click());
        onView(withId(R.id.done)).perform(click());

        Helper.navigateUp();
        onView(withId(R.id.date)).check(matches(isDisplayed()));
    }

    //all below this should be removed

    @Test
    @Ignore
    public void testCompletedTasksAreCleared(){

        Helper.closeDrawer();

        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};
        Helper.createNotes(noteNames);

        //todo remove this assert, it is silly and not what we need
//        onView(withRecyclerView(android.R.id.list).atPosition(3))
//                .check(matches(hasDescendant(withText(noteName1))));

        //todo click the checkbox in a couple rows

//        onView(withId(R.id.checkbox)).perform(click());

//        onView(withId(R.id.taskitemCard)).perform(
//                RecyclerViewActions.actionOnItemAtPosition(1, new CheckCheckBox())
//        );

//        MyViewAction action = new MyViewAction();

        clickCheckBoxAt(1);
        clickCheckBoxAt(3);

        //clear notes
        onView(withContentDescription("More options")).perform(click());
        onView(withId(R.id.title)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        //check that the notes do not exist any more
        onView(withText(noteNames[0]))
                .check(doesNotExist());
        onView(withText(noteNames[2]))
                .check(doesNotExist());


    }

    private void clickCheckBoxAt(int position) {
        onView(withId(android.R.id.list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(position, Helper.MyViewAction.clickChildViewWithId(
                        R.id.checkbox
                ))
        );
    }

    private static final class ActivityFinisher implements Runnable {

        public static void finishOpenActivities() {
            new Handler(Looper.getMainLooper()).post(new ActivityFinisher());
        }

        private final ActivityLifecycleMonitor activityLifecycleMonitor;

        private ActivityFinisher() {
            this.activityLifecycleMonitor = ActivityLifecycleMonitorRegistry.getInstance();
        }

        @Override
        public void run() {
            final List<Activity> activities = new ArrayList<Activity>();

            for (final Stage stage : EnumSet.range(Stage.CREATED, Stage.STOPPED)) {
                activities.addAll(activityLifecycleMonitor.getActivitiesInStage(stage));
            }

            for (final Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }
}
