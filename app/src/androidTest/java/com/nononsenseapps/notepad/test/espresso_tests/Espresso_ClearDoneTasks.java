package com.nononsenseapps.notepad.test.espresso_tests;

import android.content.res.Resources;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Helper;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Espresso_ClearDoneTasks {

    String noteName1, noteName2, noteName3, noteName4;

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
    public void testCompletedTasksAreCleared(){

        Helper.closeDrawer();

        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};
        Helper.createNotes(noteNames);
        clickCheckBoxAt(1);
        clickCheckBoxAt(3);

        //clear notes
        onView(withContentDescription("More options")).perform(click());
//        onView(withId(R.id.title)).perform(click());
        onView(withText("Clear completed")).perform(click());
//        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("OK")).perform(click());

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

    public class CheckCheckBox implements ViewAction{

        ViewAction action = click();

        @Override
        public Matcher<View> getConstraints() {
            return null;
        }

        @Override
        public String getDescription() {
            return "set checkbox checked";
        }

        @Override
        public void perform(UiController uiController, View view) {
            action.perform(uiController, view.findViewById(R.id.checkbox));
        }
    }



    //https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
    //https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e#.vxcgg4frc

    // Convenience helper
    private RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }


    private class RecyclerViewMatcher {
        private final int recyclerViewId;

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;

                View childView;

                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    new Object[] { Integer.valueOf
                                            (recyclerViewId) });
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {
                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView =
                                (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            RecyclerView.ViewHolder viewHolder =
                                    recyclerView.findViewHolderForAdapterPosition(position);
                            if (viewHolder != null) {
                                childView = viewHolder.itemView;
                            }
                        }
                        else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }
                }
            };
        }
    }

}
