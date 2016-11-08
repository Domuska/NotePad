package com.nononsenseapps.notepad.test;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.nononsenseapps.notepad.database.Task;
import com.nononsenseapps.notepad.database.TaskList;

import java.text.DateFormat;
import java.util.Date;


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

	public static String getMonthAndYear(){

		String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
		//June 27, 2016
		String month = date.substring(0, date.indexOf(" "));
//        String day = date.substring(date.indexOf(" ")+1, date.indexOf(","));

		//not the neatest way to do this, but should work until 2100 period
		String year =  date.substring(date.indexOf("20"), date.indexOf("20")+4);


		return month + " " + year;
		//16 June 2016
	}

	@NonNull
	public static String getCurrentMonthName() {
		String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());

		int firstSpaceIndex = date.indexOf(" ");
		String dateReturned = date.substring(0, firstSpaceIndex);

		dateReturned += date.substring(firstSpaceIndex, date.lastIndexOf(" "));

		//trim the trailing , from the string
		dateReturned = dateReturned.substring(0, dateReturned.length()-1);

		return dateReturned;
	}







}
