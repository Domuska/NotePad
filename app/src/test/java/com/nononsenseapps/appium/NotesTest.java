package com.nononsenseapps.appium;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class NotesTest {

    private AndroidDriver<WebElement> driver;
    private WebDriverWait driverWait;
    private final String TASK_LIST_TITLE = "Notes";
    private String taskListName = "a random task list";
    private String noteName1 = "prepare food";
    private String noteName2 = "take dogs out";
    private String noteName3 = "water plants";
    private String noteName4 = "sleep";
    private String[] noteNameList = {noteName1, noteName2, noteName3, noteName4,
                                    "go for a jog", "do some work", "play with the dog",
                                    "work out", "do weird stuff", "read a book", "drink water",
                                    "write a book", "proofread the book", "publish the book",
                                    "ponder life", "build a house", "repair the house", "call contractor",
                                    "write another book", "scrap the book project", "start a blog",
                                    "  ", "     "};
    private String[] taskListNames = {"Lorem", "ipsum ", "dolor ", "sit ", "amet", "consectetur ",
            "adipiscing ", "elit", "sed ", "do ", "eiusmod ", "tempor ", "incididunt ",
            "ut ", "labore "};


    @Before
    public void setUp() throws Exception{
//        File classpathRoot = new File(System.getProperty("user.dir"));
//        File app = new File(classpathRoot, "nononsensenotes-debug.apk");
//        System.out.println(app.getAbsolutePath());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Nexus 5x 1");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability(MobileCapabilityType.APP,
                "C:\\Users\\Tomi\\Projects\\notepad_own_fork\\NotePad\\nononsensenotes-6.0.0-beta.5-11-g3859c1d-free-debug.apk");
        capabilities.setCapability("appPackage", "com.nononsenseapps.notepad");
        capabilities.setCapability("appActivity", ".activities.ActivityList");

        //commands to shut down the app and clear app data between tests
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("noReset", false);

        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driverWait = new WebDriverWait(driver, 20);

        driver.launchApp();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("com.nononsenseapps.notepad:id/navigation_drawer")
        ));
    }

    @After
    public void tearDown() throws Exception{
        if(driver != null)
            driver.quit();
    }

    @Test
    public void testAddTaskListCheckItIsAddedToDrawer(){

        createTaskList(taskListName);

        driver.findElementByAccessibilityId("Open navigation drawer").click();

        driver.findElement
                (By.xpath("//*[@text='" + taskListName + "']"));

        //should modify this test so that we check the visibility of the element
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
//                assertVisibleText(taskListName)
//        ));
    }



    @Test
    public void testAddNewNoteShouldShowNameInNotesScreen(){

        closeDrawer();
        createNewNoteWithName(noteName1);
        navigateUp();

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ noteName1 + "']")
                )
        );

        //assert the element is visible
        assertVisibleText(noteName1);
    }


    @Test
    public void testAddNewNoteWithDueDateCheckDateIsVisible(){

        closeDrawer();

        createNewNoteWithName(noteName1);
        driver.hideKeyboard();

        driver.findElement(By.id("com.nononsenseapps.notepad:id/dueDateBox")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();
        navigateUp();

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ noteName1 + "']")
                )
        );

        //should find the element even if its' visibility is set to invisible
        driver.findElement(By.id("com.nononsenseapps.notepad:id/date"));
    }

    @Test
    public void testCreateNoteAndDeleteIt(){

        //first wait that the app is actually started
        driverWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("com.nononsenseapps.notepad:id/drawer_layout")
                )
        );

        //close drawer and create the note
        closeDrawer();
        createNewNoteWithName(noteName1);
        navigateUp();

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ noteName1 + "']")
                )
        );

        //open the note and delete it
        driver.findElement(By.xpath("//*[@text='"+ noteName1 + "']")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/menu_delete")).click();
        driver.findElement(By.id("android:id/button1")).click();

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ TASK_LIST_TITLE + "']")
                )
        );

        //make sure the note is not visible any more
        List<WebElement> elements = driver.findElements(By.xpath("//*[@text='"+ noteName1 + "']"));
        assertEquals(0, elements.size());
    }


    @Test
    public void testAddNoteToTaskList(){
        createTaskList(taskListName);

        //open the task list
        openDrawer();
        driver.findElement(By.xpath("//*[@text='"+ taskListName +"']")).click();

        //add the note
        createNewNoteWithName(noteName1);
        navigateUp();

        //assert the drawer shows the task list has a task
        openDrawer();
        List<WebElement> elements = driver.findElements(By.id("android:id/text2"));

        boolean taskNumberFound = false;
        for(WebElement element : elements){
            if(element.getText().equals("1"))
                taskNumberFound = true;
        }
        assertTrue("Number of tasks in task list is not found" , taskNumberFound);
    }


    @Test
    @Ignore
    public void testAddNotesOrderByDueDate(){

        closeDrawer();
        String[] expectedNoteOrder = {noteName2, noteName1, noteName4, noteName3};

        String day04 = "04" + getMonthAndYear();
        String day05 = "05" + getMonthAndYear();
        String day15 = "15" + getMonthAndYear();
        String day23 = "23" + getMonthAndYear();

        //day 05
        createNewNoteWithName(noteName1);
        driver.findElement(By.xpath("//*[@text='Due date']")).click();
        driver.findElementByAccessibilityId(day05).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();
        navigateUp();

        //day 04
        createNewNoteWithName(noteName2);
        driver.findElement(By.xpath("//*[@text='Due date']")).click();
        driver.findElementByAccessibilityId(day04).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();
        navigateUp();

        //day 23
        createNewNoteWithName(noteName3);
        driver.findElement(By.xpath("//*[@text='Due date']")).click();
        driver.findElementByAccessibilityId(day23).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();
        navigateUp();

        //day 15
        createNewNoteWithName(noteName4);
        driver.findElement(By.xpath("//*[@text='Due date']")).click();
        driver.findElementByAccessibilityId(day15).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();
        navigateUp();

        //order by due date
        driver.findElement(By.id("com.nononsenseapps.notepad:id/menu_sort")).click();
        driver.findElement(By.xpath("//*[@text='Order by due date']")).click();

        //rely on the fact that in a recyclerview the elements always have the same ID
        List<WebElement> elements = getNotesInNotesList();

        assertEquals(4, elements.size());
        for(int i = 0; i < elements.size(); i++){
            assertEquals(expectedNoteOrder[i], elements.get(i).getText());
        }
    }


    @Test
    public void testCreateTaskListAndDeleteIt(){

        //create the tasklist
        createTaskList(taskListName);
        openDrawer();

        //delete the tasklist
        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ taskListName + "']")
                )
        );

        //long press the element
        WebElement taskList = driver.findElement(By.xpath("//*[@text='"+ taskListName + "']"));
        TouchAction longPress = new TouchAction(driver);
        longPress.longPress(taskList, 2000).release().perform();

        driver.findElement(By.id("com.nononsenseapps.notepad:id/deleteButton")).click();

        driver.findElement(By.xpath("//*[@text='OK']")).click();

        //get the list of elements with the name that is used to create the element, should be 0
        List<WebElement> elements = driver.findElements(By.xpath("//*[@text='"+ taskListName + "']"));
        assertEquals(0, elements.size());
    }


    @Test
    public void testCompletedTasksAreCleared(){
        closeDrawer();

        String [] noteNames = {noteName1, noteName2, noteName3, noteName4};
        createNotes(noteNames);

        List<WebElement> checkBoxes =  driver.findElements(By.id("com.nononsenseapps.notepad:id/checkbox"));
        checkBoxes.get(1).click();
        checkBoxes.get(3).click();

        driver.findElementByAccessibilityId("More options").click();
        driver.findElement(By.xpath("//*[@text='Clear completed']")).click();
        driver.findElement(By.xpath("//*[@text='OK']")).click();

        List<WebElement> remainingTasks = getNotesInNotesList();
        List<String> noteTitles = new ArrayList<String>();
        for(int i = 0; i < noteTitles.size(); i++){
            noteTitles.add(remainingTasks.get(i).getText());
        }

        assertFalse(noteTitles.contains(noteNames[1]));
        assertFalse(noteTitles.contains(noteNames[3]));
    }


    @Test
    @Ignore
    public void testAddBigNumberOfNotesScrollDownAndDeleteOne(){

        driverWait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("com.nononsenseapps.notepad:id/drawer_layout")
                )
        );

        closeDrawer();
        createNotes(noteNameList);

        scrollInListTo(driver, noteNameList[0]);
        driver.findElement(By.xpath("//*[@text='"+ noteNameList[0] + "']")).click();

        driver.findElement(By.id("com.nononsenseapps.notepad:id/menu_delete")).click();
        driver.findElement(By.id("android:id/button1")).click();

        //todo is this really a good way to do this?
        // there are no ways in Appium to assert that something is not visible
        boolean noteVisible = true;
        try{
            scrollInListTo(driver, noteNameList[0]);
        }
        catch(NoSuchElementException e){
            noteVisible = false;
        }
        assertFalse("Note is not removed from list", noteVisible);
    }

    @Test
    public void testAddNewNoteWithReminderDateAndTime(){

        closeDrawer();
        createNewNoteWithName(noteName1);
        driver.hideKeyboard();

        //add reminder
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationAdd")).click();

        //add date
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationDate")).click();
        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='Done']"))
        );
        driver.findElement(By.id("com.nononsenseapps.notepad:id/done")).click();

        //add time
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationTime")).click();

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.id("com.nononsenseapps.notepad:id/done_button"))
        );

        driver.findElement(By.id("com.nononsenseapps.notepad:id/done_button")).click();

        navigateUp();

        //check that the date field is visible
        driver.findElement(By.xpath("//*[@text='" + noteName1 + "']")).click();
        driver.hideKeyboard();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/notificationDate"));
    }

    @Test
    public void testAddTaskListAndRotateScreen(){
        createTaskList(taskListName);

        openDrawer();

        driver.rotate(ScreenOrientation.LANDSCAPE);
        driver.rotate(ScreenOrientation.PORTRAIT);
        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='" + taskListName + "']"))
        );
    }

    @Test
    public void testAddNotesAndRotateScreen(){
        String[] noteNames = {noteName1, noteName2, noteName3, noteName4};

        closeDrawer();
        createNotes(noteNames);

        //rotate screen
        driver.rotate(ScreenOrientation.LANDSCAPE);
        driver.rotate(ScreenOrientation.PORTRAIT);

        assertTrue("element: " + noteNameList[0] + " is not displayed",
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@text='"+ noteNameList[0] + "']")))
                        .isDisplayed());
        assertTrue("element: " + noteNameList[1] + " is not displayed",
                driver.findElement(By.xpath("//*[@text='"+ noteNameList[1] + "']")).isDisplayed());
        assertTrue("element: " + noteNameList[2] + " is not displayed",
                driver.findElement(By.xpath("//*[@text='"+ noteNameList[2] + "']")).isDisplayed());
        assertTrue("element: " + noteNameList[3] + " is not displayed",
                driver.findElement(By.xpath("//*[@text='"+ noteNameList[3] + "']")).isDisplayed());
    }

    @Test
    public void testAddTaskListsScrollNavigationDrawer(){

        for(String name : taskListNames){
            createTaskList(name);
            openDrawer();
        }

        scrollInListTo(driver, "Settings").click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='Appearance']")
        ));
    }


    // HELPERS

    private By assertVisibleText(String text) {
        return By.xpath("//UIAStaticText[@visible=\"true\" and (@name=\"" + text
                + "\" or @hint=\"" + text + "\" or @label=\"" + text
                + "\" or @value=\"" + text + "\""
                + " or @text=\"" + text + "\"" + ")]");
    }

    private void createNewNoteWithName(String name){
        driver.findElement(By.id("com.nononsenseapps.notepad:id/fab")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/taskText")).sendKeys(name);
    }

    private void createNotes(String[] noteNames){
        for (int i = 0; i < noteNames.length; i++){
            createNewNoteWithName(noteNames[i]);
            navigateUp();
        }
    }

    private void createTaskList(String name) {
        scrollInListTo(driver, "Create new");
        driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Create')]")).click();
        driver.findElement(By.id("com.nononsenseapps.notepad:id/titleField")).sendKeys(name);
        driver.findElement(By.id("com.nononsenseapps.notepad:id/dialog_yes")).click();
    }


    private List<WebElement> getNotesInNotesList() {
        return (List<WebElement>) new ArrayList<WebElement>(driver.findElementsByAccessibilityId("Item title"));
    }

    private void navigateUp() {
        driver.findElementByAccessibilityId("Navigate up").click();

    }

    private void closeDrawer(){
        WebElement drawerLayout = driver.findElementById("com.nononsenseapps.notepad:id/navigation_drawer");

        //find the middle point of the drawer layout
        Dimension dimension = drawerLayout.getSize();
        Point point = new Point(dimension.getWidth()/2, dimension.getHeight()/2);

        //swipe from the middle to left edge to close drawer
        driver.swipe(point.getX(), point.getY(), 1, point.getY(), 300);
    }

    private WebElement scrollInListTo(AndroidDriver driver, String elementName){
        //apparently this is the way to do it, according to
        // https://github.com/appium/java-client/issues/421
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)" +
                ".instance(0)).scrollIntoView(new UiSelector()" +
                ".textContains(\"" + elementName + "\").instance(0))" );
    }

    private void openDrawer() {

        driver.findElementByAccessibilityId("Open navigation drawer").click();
    }

    private String getMonthAndYear(){

        String date = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        //27 June 2016
        String month = date.substring(date.indexOf(" "), date.lastIndexOf(" "));
//        String day = date.substring(date.indexOf(" ")+1, date.indexOf(","));

        //not the neatest way to do this, but should work until 2100 period
        String year =  date.substring(date.indexOf("20"), date.indexOf("20")+4);

        return month + " " + year;
        //16 June 2016
    }


}
