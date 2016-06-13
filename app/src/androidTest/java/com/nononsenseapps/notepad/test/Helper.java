package com.nononsenseapps.notepad.test;

import com.nononsenseapps.notepad.*;
import com.nononsenseapps.notepad.database.Task;
import com.nononsenseapps.notepad.database.TaskList;

import android.content.Context;
import android.database.Cursor;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class Helper {

	public static String NAVIGATE_UP_TEXT = "Navigate up";

	public static Task getATask(final Context context) {
		Cursor c = context.getContentResolver().query(Task.URI, Task.Columns.FIELDS, null, null, null);	
		Task result =  null;
		if (c.moveToFirst())
			result = new Task(c);
		return result;
	}
	
	public static TaskList getATaskList(final Context context) {
		Cursor c = context.getContentResolver().query(TaskList.URI, TaskList.Columns.FIELDS, null, null, null);	
		TaskList result =  null;
		if (c.moveToFirst())
			result = new TaskList(c);
		return result;
	}

	public static void closeDrawer() {
		onView(withId(com.nononsenseapps.notepad.R.id.drawer_layout)).perform(DrawerActions.close());
	}

	public static void openDrawer(){
		onView(withId(com.nononsenseapps.notepad.R.id.drawer_layout)).perform(DrawerActions.open());
	}

	public static void createNoteWithName(String noteName) {
		onView(withId(com.nononsenseapps.notepad.R.id.fab)).perform(click());
		onView(withId(com.nononsenseapps.notepad.R.id.taskText)).perform(typeText(noteName));
	}

	public static void navigateUp(){
		onView(withContentDescription(Helper.NAVIGATE_UP_TEXT)).perform(click());
	}

	public static ViewInteraction scrollRecyclerViewToText(String noteName){

//		return onView(withClassName(endsWith("RecyclerView")))
//				.perform(RecyclerViewActions.scrollTo(
//						withText(noteName)
//				));

		onView(withText(noteName));

		return onView(withContentDescription("List of tasks"))
				.perform(RecyclerViewActions.scrollTo(
						withText(noteName)
				));

//		return onView(withContentDescription("List of tasks"))
//				.perform(RecyclerViewActions.scrollToPosition(5)
//				);
	}

	public static void createNotes(String[] noteNames){
		for (int i = 0; i < noteNames.length; i++){
			createNoteWithName(noteNames[i]);
			navigateUp();
		}
	}


	public static class MyViewAction{

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
