package com.nononsenseapps.notepad.test;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;

import com.nononsenseapps.notepad.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class Espresso_Helper {

	public static String NAVIGATE_UP_TEXT = "Navigate up";

	public static void closeDrawer() {
		//use the Espresso helper DrawerActions
		onView(withId(com.nononsenseapps.notepad.R.id.drawer_layout)).perform(DrawerActions.close());
	}

	public static void openDrawer(){
		//use the Espresso helper DrawerActions
		onView(withId(com.nononsenseapps.notepad.R.id.drawer_layout)).perform(DrawerActions.open());
	}

	public static void createNoteWithName(String noteName) {
		onView(withId(com.nononsenseapps.notepad.R.id.fab)).perform(click());
		onView(withId(com.nononsenseapps.notepad.R.id.taskText)).perform(typeText(noteName));
	}

	public static void createNotes(String[] noteNames){
		for (int i = 0; i < noteNames.length; i++){
			createNoteWithName(noteNames[i]);
			navigateUp();
		}
	}

	/**
	 * Add a new task list. The drawer should be open when this is called
	 * @param taskListName name of the task list
     */
	public static void createTaskList(String taskListName){
		onView(withId(R.id.navigation_drawer)).perform(RecyclerViewActions.actionOnItem(
				hasDescendant(withText("Create new")), click()
		));

		onView(withId(com.nononsenseapps.notepad.R.id.titleField)).perform(typeText(taskListName));
		onView(withId(com.nononsenseapps.notepad.R.id.dialog_yes)).perform(click());
	}

	public static void navigateUp(){
		onView(withContentDescription(Espresso_Helper.NAVIGATE_UP_TEXT)).perform(click());
	}

}
