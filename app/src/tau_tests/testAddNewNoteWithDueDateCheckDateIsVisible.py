# -*- coding: utf-8 -*-
import notesutils
class InsertTestClassNameHere(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global noteName1 
        noteName1 = "prepare food"
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')

    @testCaseInfo('<Add note with due date>', deviceCount=1)
    def testAddNewNoteWithDueDateCheckDateIsVisible(self):
        """
            1. add new note
            2. check that due date is visible in list
        """
        notesutils.closeDrawer()
        
        notesutils.createNoteWithName(noteName1)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        
        notesutils.navigateUp()
        
        verify.resourceId('com.nononsenseapps.notepad:id/date', scroll=True)