# -*- coding: utf-8 -*-
import time
class NotesTests(UITestCase):
    
    
    def setUp(self):
        #launch.app('com.nononsenseapps.notepad/.activities.ActivityList')
        #launch.activity('com.nononsenseapps.notepad', '.activities.ActivityList')
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global taskListName
        taskListName = "a random task list"
        
        global noteName1 
        noteName1 = "prepare food"
        
        global noteName2
        noteName2 = "take dogs out"
        
        global noteName3
        noteName3 = "water plants"
        
        global noteName4
        noteName4 = "sleep"
        
        global noteNamesList
        noteNamesList = ["prepare food", "take dogs out", "water plants", "sleep",
                "go for a jog", "do some work", "play with the dog",
                "work out", "do weird stuff", "read a book", "drink water",
                "write a book", "proofread the book", "publish the book",
                "ponder life", "build a house", "repair the house", "call contractor",
                "write another book", "scrap the book project", "start a blog",
                "  ", "     "]
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')
        
    @testCaseInfo('<Add a new note>', deviceCount=1)
    def testAddNewNoteShouldShowNameInNotesScreen(self):
        """ Insert brief description of the test case

            1. Close the navigation drawer
            2. Add a new note
            3. Verify that the note is in the list
           
        """
        log('Step1: Insert test step description')
        self.closeDrawer()
        
        self.createNoteWithName(noteName1)
        
        self.navigateUp()
        verify.text('prepare food', scroll=True)
        

    @testCaseInfo('<Add new notelist>', deviceCount=1)
    def testAddTaskListCheckItIsAddedToDrawer(self):
        """
            1. create a new task list
            2.verify that the task list exists 
        """
        
        self.createTaskList(taskListName)
        self.openDrawer()
        
        verify.text(taskListName, scroll=True)
        
    @testCaseInfo('<Add note to tasklist>', deviceCount=1)
    def testAddNoteToTaskList(self):
        """
            1. creat a task list
            2. open the task list and add note to it
            3. verify that nav drawer shows 1 note added
        """
        
        self.createTaskList(taskListName)
        
        tap.description('Open navigation drawer')
        tap.text(taskListName)
        
        self.createNoteWithName(noteName1)
        self.navigateUp()

        verify.text(noteName1, scroll=True)
        self.openDrawer()
        verify.text('1', scroll=True, area="com.nononsenseapps.notepad:id/navigation_drawer")
        
    @testCaseInfo('<Add note and add reminder date>', deviceCount=1)
    def testAddNewNoteWithReminderDateAndTime(self):
        """
            1. add new note
            2. add reminder date and time
            3. open note and verify the date is visible        
        """

        self.closeDrawer()
        
        self.createNoteWithName(noteName1)
        
        tap.resourceId("com.nononsenseapps.notepad:id/notificationAdd")
        
        #add date
        tap.resourceId("com.nononsenseapps.notepad:id/notificationDate")
        tap.resourceId("com.nononsenseapps.notepad:id/done")
        
        #add time
        tap.resourceId("com.nononsenseapps.notepad:id/notificationTime")
        tap.resourceId("com.nononsenseapps.notepad:id/done_button")
        
        self.navigateUp()
        
        tap.text(noteName1)
        
        #verify month is shown
        verify.resourceId('com.nononsenseapps.notepad:id/notificationDate', scroll=True)
        #found = find.resourceId('com.nononsenseapps.notepad:id/notificationDate', scroll=True)
        
        #if not found:
        #    fail("month is not visible even though reminder was added")
        
    @testCaseInfo('<Add note with due date>', deviceCount=1)
    def testAddNewNoteWithDueDateCheckDateIsVisible(self):
        """
            1. add new note
            2. check that due date is visible in list
        """
        self.closeDrawer()
        
        self.createNoteWithName(noteName1)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        
        self.navigateUp()
        
        verify.resourceId('com.nononsenseapps.notepad:id/date', scroll=True)
        #found = find.resourceId('com.nononsenseapps.notepad:id/date', scroll=True)
        
        #if not found:
        #    fail("due date is not visible!")

        
    @testCaseInfo('<Add note and delete it>', deviceCount=1)
    def testCreateNoteAndDeleteIt(self):
        """
            1. add new note
            2. delete it
            3. verify it's not in the list any more
        """
        
        self.closeDrawer()
        self.createNoteWithName(noteName1)
        self.navigateUp()
        tap.text(noteName1)
        
        tap.resourceId('com.nononsenseapps.notepad:id/menu_delete')
        tap.resourceId('android:id/button1')
        
        verify.no.text(noteName1, index=1, scroll=True)
        #found = find.text(noteName1, index=1, scroll=True)
        
        #if found:
        #    fail("note found, was not deleted properly!")
        
        
    @testCaseInfo('<Add notes and order by due date>', deviceCount=1)
    def testAddNotesOrderByDueDate(self):
        """
            1. add notes with due dates
            2. sort by due date
            3. check that notes sorted correctly
            
        """
        
        
        # initialize the strings that are used with content descriptions
        # could maybe use regexes for those too but this works
        currentMonthAndYear = \
        " " +  time.strftime("%B") + " " + time.strftime("%Y")
        date_fourth = "04" + currentMonthAndYear
        date_fifth = "05" + currentMonthAndYear
        date_fifteenth = "15" + currentMonthAndYear
        date_twentythird = "23" + currentMonthAndYear
        
        #initialize the notes in right order
        notes_correct_order = [noteName2, noteName1, noteName4, noteName3]
        
        
        self.closeDrawer()
        
        # create first note
        self.createNoteWithName(noteName1)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_fifth)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        self.navigateUp()
        
        #create second note
        self.createNoteWithName(noteName2)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_fourth)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        self.navigateUp()
        
        #third note
        self.createNoteWithName(noteName3)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_twentythird)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        self.navigateUp()
        
        #fourth note
        self.createNoteWithName(noteName4)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_fifteenth)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        self.navigateUp()
        
        #order the notes
        tap.resourceId('com.nononsenseapps.notepad:id/menu_sort')
        tap.text('Order by due date')
        
        note2_right_place = find.text(noteName2, index=0)
        note1_right_place = find.text(noteName1, index=1)
        note4_right_place = find.text(noteName4, index=2)
        note3_right_place = find.text(noteName3, index=3)
        
        
        notes = get.items.instanceOf('android.widget.TextView', \
        description='Item title')
        note_names = []
        
        #get actual note names
        for note in notes:
            note_names.append(note.Text)
        
        #check that notes are in right order
        if not (notes_correct_order == note_names):
            log("correct order of notes:")
            log(notes_correct_order)
            log("order gotten:")
            log(note_names)
            fail("notes in wrong order")
            
            
    @testCaseInfo('<Add a new task list and delete it>', deviceCount=1)
    def testCreateTaskListAndDeleteIt(self):
        """
            1. create new task list
            2. delete it
            3. assert that the task list is not visible
        """
        
        #create the task list
        tap.text('Create new')
        tap.resourceId('com.nononsenseapps.notepad:id/titleField')
        input.text(taskListName)
        tap.resourceId('com.nononsenseapps.notepad:id/dialog_yes')
        
        #delete the task list
        tap.description('Open navigation drawer')
        tap.long.text(taskListName)
        tap.resourceId('com.nononsenseapps.notepad:id/deleteButton')
        tap.text('OK')
        
        #assert it is not visible
        verify.no.text(taskListName)
        #exists.no.text(taskListName)
                
    @testCaseInfo('<Clear done tasks>', deviceCount=1)
    def testCompletedTasksAreCleared(self):
        """
            1. add notes
            2. check 2 checkboxes
            3. clear done tasks
            4. assert that correct notes are removed from list
        """
        
        noteNames = [noteName1, noteName2, noteName3, noteName4]
        
        self.closeDrawer()
        self.createNotes(noteNames)
        
        tap.instanceOf('android.widget.CheckBox', index = 1)
        tap.instanceOf('android.widget.CheckBox', index = 3)
        #checkBoxes = get.items.instanceOf('android.widget.CheckBox')
        
        #tap.item(checkBoxes[1])
        #tap.item(checkBoxes[3])
        
        #remove notes
        tap.description('More options')
        tap.text('Clear completed')
        tap.text('OK')
        
        verify.no.text(noteNames[0])
        verify.no.text(noteNames[2])
        #exists.no.text(noteNames[0])
        #exists.no.text(noteNames[2])
        
    @testCaseInfo('<Rotate screen in task list>', deviceCount=1)
    def testAddTaskListAndRotateScreen(self):
        """
            1. add a new task list
            2. rotate screen twice
            3. make sure the task list is still visible
        """
        
        #create the task list
        tap.text('Create new')
        tap.resourceId('com.nononsenseapps.notepad:id/titleField')
        input.text(taskListName)
        tap.resourceId('com.nononsenseapps.notepad:id/dialog_yes')
        
        self.openDrawer()
        
        #rotate screen
        orientation.left()
        orientation.portrait()
        
        verify.text(taskListName)
        #taskListFound = exists.text(taskListName)
        
        #if not taskListFound:
        #    fail("task list was not found")
        
        
    @testCaseInfo('<Rotate screen in task notes>', deviceCount=1)
    def testAddNotesAndRotateScreen(self):
        """
            1. add notes to the list
            2. rotate the screen 
            3. assert that notes are still visible
        """
        
        noteNames = [noteName1, noteName2, noteName3, noteName4]
        
        self.closeDrawer()
        self.createNotes(noteNames)
        
        #rotate screen
        orientation.left()
        orientation.portrait()
        
        verify.text(noteNames[0])
        verify.text(noteNames[1])
        verify.text(noteNames[2])
        verify.text(noteNames[3])
        #firstNoteFound = exists.text(noteNames[0])
        #secondNoteFound = exists.text(noteNames[1])
        #thirdNoteFound = exists.text(noteNames[2])
        #fourthNoteFound = exists.text(noteNames[3])
        
        #if not firstNoteFound or not secondNoteFound \
        #or not thirdNoteFound or not fourthNoteFound:
        #    fail("all notes were not visible in the list")
        
    @testCaseInfo('<Add notes and scroll down to open one>', deviceCount=1)
    def testAddBigNumberOfNotesScrollDownAndDeleteOne(self):
        """
            1. add a big number of notes
            2. scroll down to last one and open it
            3. assert that the last note was opened
        """
        self.closeDrawer()
        self.createNotes(noteNamesList)
        tap.text(noteNamesList[0])
       
        
    @testCaseInfo('<Add task lists and scroll down to open settings>', deviceCount=1)
    def testAddTaskListsScrollNavigationDrawer(self):
        """
            1. Add new task lists
            2. scroll down to settings
            3. assert that settings was opened
        """
        
        taskListNames= ["Lorem", "ipsum ", "dolor ", "sit ", "amet", "consectetur ",\
        "adipiscing ", "elit", "sed ", "do ", "eiusmod ", "tempor ", "incididunt ",\
        "ut ", "labore "]
        
        for name in taskListNames:
            self.createTaskList(name)
            self.openDrawer()
        
        #open settings, define that it is searched in the nav drawer
        tap.text("Settings", area="com.nononsenseapps.notepad:id/navigation_drawer")
        
        verify.text("Settings")
        #settingsOpened = exists.text("Settings")
        
        #if not settingsOpened:
        #    fail("settings was not launched")
        
    
    
    def createNoteWithName(self, noteName):
        tap.resourceId("com.nononsenseapps.notepad:id/fab")
        tap.resourceId("com.nononsenseapps.notepad:id/taskText")
        input.text(noteName)
        
    
    def createNotes(self, noteNames):
        for name in noteNames:
            self.createNoteWithName(name)
            self.navigateUp()
    
    
    def createTaskList(self, name):
        #tap in the nav drawer
        tap.text('Create new', area='com.nononsenseapps.notepad:id/navigation_drawer')
        tap.resourceId('com.nononsenseapps.notepad:id/titleField')
        input.text(name)
        tap.resourceId('com.nononsenseapps.notepad:id/dialog_yes')
        
    
    def closeDrawer(self):
        swipe.description('List of tasks').to.location((0, 0.5))
    
    def openDrawer(self):
        tap.description('Open navigation drawer')
        
    def navigateUp(self):
        tap.description('Navigate up')
        
        
