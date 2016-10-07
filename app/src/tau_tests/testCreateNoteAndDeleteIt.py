# -*- coding: utf-8 -*-
import notesutils
class testCreateNoteAndDeleteIt(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global noteName1 
        noteName1 = "prepare food"
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad') 

    @testCaseInfo('<Add note and delete it>', deviceCount=1)
    def testCreateNoteAndDeleteIt(self):
        """
            1. add new note
            2. delete it
            3. verify it's not in the list any more
        """
        
        notesutils.closeDrawer()
        notesutils.createNoteWithName(noteName1)
        notesutils.navigateUp()
        tap.text(noteName1)
        
        tap.resourceId('com.nononsenseapps.notepad:id/menu_delete')
        tap.resourceId('android:id/button1')
        
        verify.no.text(noteName1, index=1, scroll=True)