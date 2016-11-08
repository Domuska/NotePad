#util method for creating a new note
def createNoteWithName(noteName):
	tap.resourceId("com.nononsenseapps.notepad:id/fab")
	tap.resourceId("com.nononsenseapps.notepad:id/taskText")
	input.text(noteName)
	
#util method for creating a new note
def createNotes(noteNames):
	for name in noteNames:
		createNoteWithName(name)
		navigateUp()

#util method for creating a new task list
def createTaskList(name):
	#tap in the nav drawer
	tap.text('Create new', area='com.nononsenseapps.notepad:id/navigation_drawer')
	tap.resourceId('com.nononsenseapps.notepad:id/titleField')
	input.text(name)
	tap.resourceId('com.nononsenseapps.notepad:id/dialog_yes')
	
#util method for closing drawer
def closeDrawer():
	swipe.description('List of tasks').to.location((0, 0.5))

#util method for opening drawer
def openDrawer():
	tap.description('Open navigation drawer')

#util method for pressing the 'up' button 	
def navigateUp():
	tap.description('Navigate up')