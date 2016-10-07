# -*- coding: utf-8 -*-
import notesutils
class testAddTaskListAndRotateScreen(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global taskListName
        taskListName = "a random task list"
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')

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
        
        notesutils.openDrawer()
        
        #rotate screen
        orientation.left()
        orientation.portrait()
        
        verify.text(taskListName)