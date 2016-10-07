# -*- coding: utf-8 -*-
import notesutils
from datetime import datetime, time
class testAddNewNoteShouldShowNameInNotesScreen(UITestCase):

    def setUp(self):
        #FOLLOWING ROWS ONLY FOR TEST LOGGING PURPOSES, NOT PART OF TEST
        log("write starting time to .csv for measurement purposes")
        timeNow = datetime.now()
        hourNow = str(timeNow.hour)
        minuteNow = str(timeNow.minute)
        secondNow = str(timeNow.second)
        
        if timeNow.hour < 10:
            hourNow = "0" + hourNow
        if timeNow.minute < 10:
            minuteNow = "0" + minuteNow
        if timeNow.second < 10:
            secondNow = "0" + secondNow
        
        #read time to temp file, used in the last test to calculate test execution time
        tempFile = open(r"C:\Users\\Tomi\testAutomation\measurements\notes\tau\temp.txt", "w")
        tempFile.write(hourNow + "\n")
        tempFile.write(minuteNow + "\n")
        tempFile.write(secondNow)
        tempFile.close()
        #TEST CODE STARTS FROM HERE
        
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        global noteName1
        noteName1 = "prepare food"
        
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
        notesutils.closeDrawer()
        
        notesutils.createNoteWithName(noteName1)
        
        notesutils.navigateUp()
        verify.text('prepare food', scroll=True)
        