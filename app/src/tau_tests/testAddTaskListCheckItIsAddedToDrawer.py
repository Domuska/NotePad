# -*- coding: utf-8 -*-
import notesutils
class testAddTaskListCheckItIsAddedToDrawer(UITestCase):

    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global taskListName
        taskListName = "a random task list"
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')

    @testCaseInfo('<Add new notelist>', deviceCount=1)
    def testAddTaskListCheckItIsAddedToDrawer(self):
        """
            1. create a new task list
            2.verify that the task list exists 
        """
        
        notesutils.createTaskList(taskListName)
        notesutils.openDrawer()
        
        verify.text(taskListName, scroll=True)