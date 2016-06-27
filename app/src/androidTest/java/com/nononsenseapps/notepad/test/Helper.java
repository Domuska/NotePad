package com.nononsenseapps.notepad.test;

import com.nononsenseapps.notepad.*;
import com.nononsenseapps.notepad.database.Task;
import com.nononsenseapps.notepad.database.TaskList;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import java.text.DateFormat;
import java.util.Date;

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

}
