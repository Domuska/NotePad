package com.nononsenseapps.notepad.test.robotium_tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import com.nononsenseapps.notepad.activities.ActivityList;
import com.robotium.solo.Solo;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

@SuppressWarnings("rawtypes")
public class ExampleTest extends ActivityInstrumentationTestCase2 <ActivityList>{
    private Solo solo;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME =
            "com.nononsenseapps.notepad.ActivityList";

//    private static Class<?> launcherActivityClass;
//    static{
//        try {
//            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @SuppressWarnings("unchecked")
    public ExampleTest(){
        super(ActivityList.class);
    }

    public void setUp() throws Exception {
//        super.setUp();
//        System.out.println(getInstrumentation().getTargetContext().getApplicationInfo().dataDir);
        Util.deleteDir(new File(getInstrumentation().getTargetContext().getApplicationInfo().dataDir));
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRun() throws Exception{
        //Wait for activity: 'com.example.ExampleActivty'
        solo.waitForActivity("ActivityList", 2000);
//        solo.clickOnActionBarHomeButton();
        //can't really use anything but this index to get the view, it has no text nor ID
//        ImageButton button = solo.getImageButton(0);
//        solo.clickOnImageButton(0);
//        solo.clickOnText("Create new");
//        solo.clickOnView(button);
        solo.clickOnText("Create new");

        //Clear the EditText editText1
//        solo.clearEditText((android.widget.EditText) solo.getView("editText1"));
//        solo.enterText((android.widget.EditText) solo.getView("editText1"), "This is an example text");
    }

    private static final class Util {

        public static boolean deleteDir(File dir) {

            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        }
    }
}