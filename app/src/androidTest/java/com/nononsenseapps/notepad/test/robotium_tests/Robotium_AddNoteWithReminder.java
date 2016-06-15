package com.nononsenseapps.notepad.test.robotium_tests;

import android.support.annotation.NonNull;
import android.test.ActivityInstrumentationTestCase2;


import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.activities.ActivityList;
import com.nononsenseapps.notepad.prefs.MainPrefs;
import com.robotium.solo.Solo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Robotium_AddNoteWithReminder extends ActivityInstrumentationTestCase2<ActivityList> {


    private Solo solo;
    private String noteName1 = "prepare food";

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";


    public Robotium_AddNoteWithReminder(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testAddNewNoteWithReminderDateAndTime(){

        Robotium_Helper.closeDrawer(solo);
        Robotium_Helper.createNoteWithName(solo, noteName1);

        //add notification
        solo.clickOnView(solo.getView(R.id.notificationAdd));

        //add date
        solo.clickOnView(solo.getView(R.id.notificationDate));
        solo.clickOnView(solo.getView(R.id.done));

        //add time
        solo.clickOnView(solo.getView(R.id.notificationTime));
        solo.clickOnView(solo.getView(R.id.done_button));

        Robotium_Helper.navigateUp(solo);

        //open note and check that date is visible
        solo.clickOnText(noteName1);
        solo.getView(R.id.notificationDate);




        String[] ids = TimeZone.getAvailableIDs(+2 * 60 * 60 * 1000);

        SimpleTimeZone finnishTimeZone = new SimpleTimeZone(+2*60*60*1000,ids[0]);

        // set up rules for daylight savings time
        finnishTimeZone.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        finnishTimeZone.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

        // create a GregorianCalendar with the Finnish time zone
        // and the current date and time
        Calendar calendar = new GregorianCalendar(finnishTimeZone);
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        int monthNumber = calendar.get(Calendar.MONTH);
        String monthName = getMonthNameFromNumber(monthNumber);

//        assertEquals(1, monthName + " "
//                + calendar.get(Calendar.MONTH) + " "
//                + calendar.get(Calendar.DATE) + " "
//                + calendar.get(Calendar.YEAR));


        String month = Robotium_Helper.getCurrentMonthName();
        Boolean rightMonthDisplayed  = solo.searchText(month);

        assertTrue("Display right month("+month+")", rightMonthDisplayed);

    }



    private String getMonthNameFromNumber(int monthNumber) {
        String monthName;

        switch(monthNumber){
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
            default:
                monthName = "";
                break;
        }
        return monthName;
    }
}
