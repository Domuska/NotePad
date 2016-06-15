package com.nononsenseapps.notepad.test.robotium_tests;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.nononsenseapps.notepad.R;
import com.robotium.solo.Solo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Robotium_Helper {

    public static void closeDrawer(Solo solo){
        Point deviceSize = new Point();
        solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(deviceSize);

        int screenWidth = deviceSize.x;
        int screenHeight = deviceSize.y;

        int fromX = screenWidth/2;
        int fromY = screenHeight/2;
        int toX = 0;
        int toY = fromY;

        solo.drag(fromX, toX, fromY, toY, 2);
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


    @NonNull
    public static String getCurrentMonthName() {
        String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        return date.substring(0, date.indexOf(" "));
    }
}
