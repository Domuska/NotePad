# -*- coding: utf-8 -*-
import notesutils
class testAddNotesAndRotateScreen(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
            
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

    @testCaseInfo('<Rotate screen in task notes>', deviceCount=1)
    def testAddNotesAndRotateScreen(self):
        """
            1. add notes to the list
            2. rotate the screen 
            3. assert that notes are still visible
        """
        
        noteNames = [noteName1, noteName2, noteName3, noteName4]
        
        notesutils.closeDrawer()
        notesutils.createNotes(noteNames)
        
        #rotate screen
        orientation.left()
        orientation.portrait()
        
        verify.text(noteNames[0])
        verify.text(noteNames[1])
        verify.text(noteNames[2])
        verify.text(noteNames[3])