# -*- coding: utf-8 -*-
import notesutils
class testAddNewNoteWithReminderDateAndTime(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global noteName1 
        noteName1 = "prepare food"
    
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')

    @testCaseInfo('<Add note and add reminder date>', deviceCount=1)
    def testAddNewNoteWithReminderDateAndTime(self):
        """
            1. add new note
            2. add reminder date and time
            3. open note and verify the date is visible        
        """
    
        notesutils.closeDrawer()
        
        notesutils.createNoteWithName(noteName1)
        
        tap.resourceId("com.nononsenseapps.notepad:id/notificationAdd")
        
        #add date
        tap.resourceId("com.nononsenseapps.notepad:id/notificationDate")
        tap.resourceId("com.nononsenseapps.notepad:id/done")
        
        #add time
        tap.resourceId("com.nononsenseapps.notepad:id/notificationTime")
        tap.resourceId("com.nononsenseapps.notepad:id/done_button")
        
        notesutils.navigateUp()
        
        tap.text(noteName1)
        
        #verify month is shown
        verify.resourceId('com.nononsenseapps.notepad:id/notificationDate', scroll=True)