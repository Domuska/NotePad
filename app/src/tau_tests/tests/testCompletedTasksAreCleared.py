# -*- coding: utf-8 -*-
import notesutils
class testCompletedTasksAreCleared(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList', verify=True)
        
        global noteName1 
        noteName1 = "prepare food"
        
        global noteName2
        noteName2 = "take dogs out"
        
        global noteName3
        noteName3 = "water plants"
        
        global noteName4
        noteName4 = "sleep"
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')

    @testCaseInfo('<Clear done tasks>', deviceCount=1)
    def testCompletedTasksAreCleared(self):
        """
            1. add notes
            2. check 2 checkboxes
            3. clear done tasks
            4. assert that correct notes are removed from list
        """
        noteNames = [noteName1, noteName2, noteName3, noteName4]
        
        notesutils.closeDrawer()
        notesutils.createNotes(noteNames)
        
        tap.instanceOf('android.widget.CheckBox', index = 1)
        tap.instanceOf('android.widget.CheckBox', index = 3)
        
        #remove notes
        tap.description('More options')
        tap.text('Clear completed')
        tap.text('OK')
        
        verify.no.text(noteNames[0])
        verify.no.text(noteNames[2])