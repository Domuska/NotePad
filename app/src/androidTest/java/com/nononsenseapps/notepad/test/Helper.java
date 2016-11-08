package com.nononsenseapps.notepad.test;

import android.content.Context;
import android.database.Cursor;

import com.nononsenseapps.notepad.database.Task;
import com.nononsenseapps.notepad.database.TaskList;

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
