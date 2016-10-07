# -*- coding: utf-8 -*-
import time, notesutils
class testAddNotesOrderByDueDate(UITestCase):
    
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

    @testCaseInfo('<Add path and order by due date>', deviceCount=1)
    def testAddNotesOrderByDueDate(self):
        """
            1. add notes with due dates
            2. sort by due date
            3. check that notes sorted correctly
            
        """
        
        # initialize the strings that are used with content descriptions
        # could maybe use regexes for those too but this works
        currentMonthAndYear = \
        " " +  time.strftime("%B") + " " + time.strftime("%Y")
        date_fourth = "04" + currentMonthAndYear
        date_fifth = "05" + currentMonthAndYear
        date_fifteenth = "15" + currentMonthAndYear
        date_twentythird = "23" + currentMonthAndYear
        
        #initialize the notes in right order
        notes_correct_order = [noteName2, noteName1, noteName4, noteName3]
        
        
        notesutils.closeDrawer()
        
        # create first note
        notesutils.createNoteWithName(noteName1)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_fifth)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        notesutils.navigateUp()
        
        #create second note
        notesutils.createNoteWithName(noteName2)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_fourth)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        notesutils.navigateUp()
        
        #third note
        notesutils.createNoteWithName(noteName3)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_twentythird)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        notesutils.navigateUp()
        
        #fourth note
        notesutils.createNoteWithName(noteName4)
        tap.resourceId('com.nononsenseapps.notepad:id/dueDateBox')
        tap.description(date_fifteenth)
        tap.resourceId('com.nononsenseapps.notepad:id/done')
        notesutils.navigateUp()
        
        #order the notes
        tap.resourceId('com.nononsenseapps.notepad:id/menu_sort')
        tap.text('Order by due date')
        
        note2_right_place = find.text(noteName2, index=0)
        note1_right_place = find.text(noteName1, index=1)
        note4_right_place = find.text(noteName4, index=2)
        note3_right_place = find.text(noteName3, index=3)
        
        notes = get.items.instanceOf('android.widget.TextView', \
        description='Item title')
        note_names = []
        
        #get actual note names
        for note in notes:
            note_names.append(note.Text)
        
        #check that notes are in right order
        if not (notes_correct_order == note_names):
            log("correct order of notes:")
            log(notes_correct_order)
            log("order gotten:")
            log(note_names)
            fail("notes in wrong order")