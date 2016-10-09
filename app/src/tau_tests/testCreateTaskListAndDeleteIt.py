# -*- coding: utf-8 -*-
from datetime import datetime, timedelta
class testCreateTaskListAndDeleteIt(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
        '.activities.ActivityList')
        
        global taskListName
        taskListName = "a random task list"
        
    def tearDown(self):
        packages.forceStop('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')
        
        #FOLLOWING ROWS ONLY FOR TEST LOGGING PURPOSES, NOT PART OF TEST RUN
        #with help from
        #http://stackoverflow.com/questions/3096953/difference-between-two-time-intervals-in-python
        timeNow = datetime.now()
        measurementPath = r"C:\Users\\Tomi\testAutomation\measurements\notes\tau\\"
        hourNow = str(timeNow.hour)
        minuteNow = str(timeNow.minute)
        secondNow = str(timeNow.second)
        
        #times with only 1 number cause problems when doing strptime, add 0
        if timeNow.hour < 10:
            hourNow = "0" + hourNow
        if timeNow.minute < 10:
            minuteNow = "0" + minuteNow
        if timeNow.second < 10:
            secondNow = "0" + secondNow
        
        #format test execution ending time
        endTime = hourNow + ":" + minuteNow + ":" + secondNow
        #read the start time from temp file
        tempFile = open(measurementPath + "temp.txt", "r")
        #strip extra newlines from the end of the strings
        startHour = tempFile.readline().rstrip()
        startMinute = tempFile.readline().rstrip()
        startSecond = tempFile.readline().rstrip()
        startTime = startHour + ":" + startMinute + ":" + startSecond
        log(str(startTime))
        log(str(endTime))
        
        FMT = '%H:%M:%S'
        #calculate the time it took to run the tests
        tDelta = datetime.strptime(endTime, FMT) - datetime.strptime(startTime, FMT)
        #handle the situation if day has changed between tests
        if tDelta.days < 0:
            tDelta = timedelta(days = 0,
                        seconds = tDelta.seconds, microseconds = tDelta.microseconds)
        log(tDelta.total_seconds())
        
        #write the test execution time to .csv file
        testName = "2016_10_7-12_14" + ".csv"
        filename = "tau_tests-"
        filename += testName
        log("opening .csv")
        csvFile = open(measurementPath + filename, "a")
        log("writing to csv")
      
        csvFile.write("\n" + str(tDelta.total_seconds()))
        csvFile.close()
        

    @testCaseInfo('<Add a new task list and delete it>', deviceCount=1)
    def testCreateTaskListAndDeleteIt(self):
        """
            1. create new task list
            2. delete it
            3. assert that the task list is not visible
        """
        
        #create the task list
        tap.text('Create new')
        tap.resourceId('com.nononsenseapps.notepad:id/titleField')
        input.text(taskListName)
        tap.resourceId('com.nononsenseapps.notepad:id/dialog_yes')
        
        #delete the task list
        tap.description('Open navigation drawer')
        tap.long.text(taskListName)
        tap.resourceId('com.nononsenseapps.notepad:id/deleteButton')
        tap.text('OK')
        
        #assert it is not visible
        verify.no.text(taskListName)