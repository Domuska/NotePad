# -*- coding: utf-8 -*-
import notesutils
class testAddNoteToTaskList(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global taskListName
        taskListName = "a random task list"
        
        global noteName1 
        noteName1 = "prepare food"

    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')        

    @testCaseInfo('<Add note to tasklist>', deviceCount=1)
    def testAddNoteToTaskList(self):
        """
            1. creat a task list
            2. open the task list and add note to it
            3. verify that nav drawer shows 1 note added
        """
        
        notesutils.createTaskList(taskListName)
        
        tap.description('Open navigation drawer')
        tap.text(taskListName)
        
        notesutils.createNoteWithName(noteName1)
        notesutils.navigateUp()
    
        verify.text(noteName1, scroll=True)
        notesutils.openDrawer()
        verify.text('1', scroll=True, area="com.nononsenseapps.notepad:id/navigation_drawer")