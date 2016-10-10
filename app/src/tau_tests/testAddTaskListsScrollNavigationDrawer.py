# -*- coding: utf-8 -*-
import notesutils
class testAddTaskListsScrollNavigationDrawer(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList', verify=True)
        
        global taskListNames
        taskListNames = ["Lorem", "ipsum ", "dolor ", "sit ", "amet", "consectetur ",\
        "adipiscing ", "elit", "sed ", "do ", "eiusmod ", "tempor ", "incididunt ",\
        "ut ", "labore "]
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')

    @testCaseInfo('<Add task lists and scroll down to open settings>', deviceCount=1)
    def testAddTaskListsScrollNavigationDrawer(self):
        """
            1. Add new task lists
            2. scroll down to settings
            3. assert that settings was opened
        """

        for name in taskListNames:
            notesutils.createTaskList(name)
            notesutils.openDrawer()
        
        #open settings so we scroll nav drawer, define that it is searched in the nav drawer
        tap.text("Settings", area="com.nononsenseapps.notepad:id/navigation_drawer")
        
        verify.text("Settings")