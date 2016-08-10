package com.nononsenseapps.notepad.test.espresso_tests;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.test.Helper;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import com.google.common.truth.Truth;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.view.View.FIND_VIEWS_WITH_TEXT;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.fail;

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

        onView(withId(android.R.id.list)).check(doesntHaveViewWithText(noteNameList[0]));

    }

    //credit to Chemouna @ GitHub,
    // https://gist.github.com/chemouna/00b10369eb1d5b00401b
    private static ViewAssertion doesntHaveViewWithText(final String text) {
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                for (int index = 0; index < rv.getAdapter().getItemCount(); index++) {
                    rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews, text,
                            FIND_VIEWS_WITH_TEXT);
                    if (outviews.size() > 0) break;
                }
                assertThat(outviews).isEmpty();
            }
        };
    }


}
