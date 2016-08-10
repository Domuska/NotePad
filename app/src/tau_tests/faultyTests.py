# -*- coding: utf-8 -*-
class FaultyTests(UITestCase):
    
    def setUp(self):
        launch.activity('com.nononsenseapps.notepad',\
                '.activities.ActivityList', wait=5)
        
        global noteName1 
        noteName1 = "prepare food"
        
        
    def tearDown(self):
        kill('com.nononsenseapps.notepad')
        packages.clearData('com.nononsenseapps.notepad')
    

    @testCaseInfo('<Add note and search with wrong name>', deviceCount=1)
    def testAddNewNoteSearchForFaultyNoteName(self):
        self.closeDrawer()
        
        self.createNoteWithName(noteName1)
        self.navigateUp()
        
        searchWord = noteName1 + "sdfa"
        
        found = exists.text(searchWord)
        
        if not found:
            fail("Note was not found with name: " + searchWord)  
        
        
    @testCaseInfo('<Search for text that is not in view and try to click it>', deviceCount=1)
    def testSearchForElementWithTextShouldFailOnView(self):
        self.closeDrawer()
        tap.text("Create new")
        fail("should have failed before this")
        
    
    @testCaseInfo('<Search with faulty ID that is in the app available but not visible>', deviceCount=1)
    def testSearchForElementWithIDShouldFailOnView(self):
        self.closeDrawer()
        tap.resourceId("com.nononsenseapps.notepad:id/fab")
        tap.resourceId("com.nononsenseapps.notepad:id/fab")
        
        fail("should have failed before this")
        
    @testCaseInfo('<Search with faulty ID that is not in the app>', deviceCount=1)
    def testSearchForElementWithFaultyID(self):
        self.closeDrawer()
        tap.resourceId("com.nononsenseapps.notepad:id/fab" + "asdf")
        fail("should have failed before this")
        
    @testCaseInfo('<Search with ambiguous text identifier>', deviceCount=1)
    def testSearchForElementWithAmbiguousIdentifier(self):
        self.closeDrawer()
        
        self.createNoteWithName(noteName1)
        self.navigateUp()
        
        self.createNoteWithName(noteName1)
        self.navigateUp()
        
        tap.text(noteName1)
        fail("should have failed before this")
        
        
    # HELPERS
        
    def createNoteWithName(self, noteName):
        tap.resourceId("com.nononsenseapps.notepad:id/fab")
        tap.resourceId("com.nononsenseapps.notepad:id/taskText")
        input.text(noteName)
        
    def closeDrawer(self):
        swipe.description('List of tasks').to.location((0, 0.5))
        
    def navigateUp(self):
        tap.description('Navigate up')