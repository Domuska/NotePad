package com.nononsenseapps.notepad.test.robotium_tests;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.nononsenseapps.notepad.R;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Robotium_Helper {

    public static void closeDrawer(final Solo solo){
        Point deviceSize = new Point();
        solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(deviceSize);

        int screenWidth = deviceSize.x;
        int screenHeight = deviceSize.y;
        int middleHeight = screenHeight/2;

        //click a bit to left from the right edge of screen, in middle
        solo.clickOnScreen(screenWidth - 20, middleHeight);

//        solo.waitForText("Notes");
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return solo.getText("Notes").isShown();
            }
        }, 5000);
    }

    //sending solo.setNavigationDrawer(Solo.OPENED) does not work, do this, credit to swanson on stackoverflow
    //http://stackoverflow.com/questions/21848875/robotium-ui-testing-for-app-with-navigation-drawer
    public static void openDrawer(Solo solo){

        Point deviceSize = new Point();
        solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(deviceSize);

        int screenWidth = deviceSize.x;
        int screenHeight = deviceSize.y;
        int fromX = 0;
        int toX = screenWidth / 2;
        int fromY = screenHeight / 2;
        int toY = fromY;

        solo.drag(fromX, toX, fromY, toY, 4);

    }

    public static void createNoteWithName(Solo solo, String noteName){

        View fab = solo.getView(R.id.fab);
        solo.clickOnView(fab);

        EditText editText = (EditText) solo.getView(R.id.taskText);
        solo.enterText(editText, noteName);
    }

    public static void createNotes(Solo solo,String[] noteNames){
        for(int i = 0; i < noteNames.length; i++){
            createNoteWithName(solo, noteNames[i]);
            navigateUp(solo);
        }

    }

    public static void navigateUp(Solo solo){
        solo.clickOnActionBarHomeButton();
    }

    /**
     * Create a new task list, nav drawer needs to be open when this is called
     * @param solo solo
     * @param taskListName name of the task list
     */
    public static void createTaskList(Solo solo, String taskListName){

//        solo.scrollRecyclerViewToBottom(1);
        solo.clickOnText("Create new");
        solo.enterText(
                (EditText)solo.getView(R.id.titleField),
                taskListName
        );
        solo.clickOnView(solo.getView(R.id.dialog_yes));
    }


    //there could be smarter way to do this and the method below, but oh well
    @NonNull
    public static String getCurrentMonthNameAndDay() {
        String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());

        int firstSpaceIndex = date.indexOf(" ");
        String dateReturned = date.substring(0, firstSpaceIndex);

        dateReturned += date.substring(firstSpaceIndex, date.lastIndexOf(" "));

        //trim the trailing , from the string
        dateReturned = dateReturned.substring(0, dateReturned.length()-1);

        return dateReturned;
    }

//    @NonNull
//    public static String getDateAndMonth(){
//
//        String date = getCurrentMonthNameAndDay();
//
//        String dateReturned = date.substring(0, date.indexOf(" "));
//        dateReturned += date.substring(date.indexOf(" "), date.length());
//
//        return dateReturned;
//    }

    public static String getCurrentMonth(){
        String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());

        int firstSpaceIndex = date.indexOf(" ");
        return date.substring(0, firstSpaceIndex);
    }

    public static String getDateAndMonth(){

        String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        //June 27, 2016
        String month = date.substring(0, date.indexOf(" "));
//        String day = date.substring(date.indexOf(" ")+1, date.indexOf(","));

        //not the neatest way to do this, but should work until 2100 period
        String year =  date.substring(date.indexOf("20"), date.indexOf("20")+4);


        return month + " " + year;
        //16 June 2016
    }
}
