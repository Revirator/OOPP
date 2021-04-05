### Manual Test Plan (application frontend)


This test plan describes how our application can be tested manually, with the main focus on our frontend GUI interface, as this aspect is rather inconvenient to write tests for. The following will be covered:
•	Navigating through the splash screen
•	Student room functionalities
•	Moderator room functionalities



##### Splash Screen:

Upon starting server and MainApp, our splash screen will open. On the top, our logo "QuestionMe" is displayed, having a blue background. In this screen, our user has several options:

######	"Join room"
-	In the text field behind "Nickname: " users can enter their desired nickname.
-	In the text field behind "Room link: " users can enter a (student/moderator) room link, which must be valid; thus corresponding to an existing room, which could be either active or inactive. (i.e. lecture ended by any moderator)
-	If both fields are filled, the user can enter a room by pressing the "Enter room" button. This will either open a student room or moderator room (link contains 'M'). In the case of 1 field being empty, an alert will display: "Please enter both nickname and link."
-	As for input sanitation, distinct alerts will be displayed whenever one of the fields contains " ", "=", "/" , or the nickname is not between 2 and 20 characters.

######	"Create room"
-	The user can create an instant room (meaning it is active from the moment "Create room" is pressed) by entering a room name in the text field behind "Room name: ". Leaving this field empty will lead to an alert upon pressing "Create room". The creator can enter a nickname behind “Nickname”.
-	The user can opt for creating a scheduled room (meaning it becomes active from the time/date provided) by selecting the checkbox ("Scheduled room?"). Now, the text fields behind "Date: " and "Hour: " should become accessible. Here, the user can respectively select a valid date from the datepicker and enter a valid time. If the date provided is in the past, or the time is not entered using valid European time notation, an alert will notify the user.
-	Upon pressing "Create room", a window will pop up, in which the generated room links can be copied. One link for students and one link for moderators will be displayed.





##### Student/Moderator Room:

For the following manual tests, it may be an idea to open several instances of the application, to be able to test interaction between students and moderators. The manual test plans below involve both the student and moderator room.


When entering a student room, the following GUI elements should be visible:
-	A blue bar on top displaying our logo "QuestionMe"
-	The room name on the right top
-	3 tabs: "Questions", "Answered Questions" and "Participants"
-	"Questions" tab: contains all unanswered questions (although, they might be answered textually by a moderator), with most recent questions on top.
-	"Answered Questions" tab: contains all questions marked as answered
-	"Participants" tab: contains all users currently in this meeting, having all moderators displayed on top, followed by all students.
-	A text box on the bottom right, in which questions can be entered and submitted by pressing the "Submit" button

When entering a moderator room, the following GUI elements should be visible:
-	A blue bar on top displaying our logo "QuestionMe", a checkbox to enable "Zen Mode", buttons for "Export questions" and "End lecture".
-	The room name on the right top
-	3 tabs: "Questions", "Answered Questions" and "Participants"
-	For the moderator “Questions” tab, the questions with most upvotes are displayed on top.
-	Statistics showing live feedback provided by students, i.e. percentages indicating how fast or slow the lecture is



###### Asking questions (Student room)
In the text box below "Ask a question", students can enter their questions. Upon pressing "Submit", this question will be sent to the server and displayed in the questions list, as long as is it is valid. Whenever a question contains fewer than 8 words or contains the symbol "&", an alert will notify the user that this is invalid (the latter symbol is used for data parsing on server side). Also, when the lecture has already ended or this student has been banned by a moderator, an alert should be displayed as well. Furthermore, a student can only ask 1 question every 20 seconds, to reduce server load. If this rate is surpassed, an alert will be displayed.
This submitted question will become visible for all users, with the nickname of the question owner attached to it.


###### Upvoting questions (Student room)
For each question displayed, users have an "upvote" button. Upon pressing this button, the current count will be incremented by 1. The list of questions will automatically become sorted according to the new count of upvotes, with questions having the most number of upvotes on top in the moderator room, and the most recent questions on top in the student room. Whenever this button is pressed again, the count will decrement by 1 again.
Moderators do not have the option to upvote questions.


###### Editing/deleting questions
Students have the option to "edit" and "delete" their own questions only, whereas moderators have these buttons available for all questions. In both cases, pressing "delete" will cause the corresponding question to get removed from the list of questions.
Upon pressing "edit", users are able to enter text, which will replace the current question in the list. If this new question is empty, an alert should be displayed.


###### Answering questions (Moderator room)
Moderators can answer questions in the form of text by pressing the "reply" button and enter an answer. If this answer is empty, an alert will be displayed. For all users, this answer, with the moderator's nickname, should now be displayed next to the corresponding question.
This question should remain in the main questions list. Only when this question is explcitly marked as answered (press “mark as answered”), it will move to the separate list of answered questions. Upon typing, all other moderators will be notified that this question is being answered. (“Someone is answering…” is displayed). To be able to test this, 2 Moderator Rooms have to be opened.


###### Mark questions as answered
Whereas students only have a "mark as answered" button for their own questions, moderators have this button for all questions. In both cases, upon pressing this button, the corresponding question will disappear from the main list of questions and be added to the separate list of answered questions, which can be found under the "Answered Questions" tab. However, moderators can additionally add an answer to in the answer box.
In this separate list, moderators have the option to edit, answer, delete all questions. Pressing any of these buttons have the same effect as the analogous buttons in the main questions list.


###### Live feedback
Students can provide live feedback by judging the speed of the lecture.
For this purpose, students have 3 buttons on the right top: "Too slow", "Too fast" and "Reset". The latter button only becomes active after one of the other two has been pressed.
Upon pressing "Too fast" or "Too slow", current statistics will be recalculated and modified to be displayed to lecturers. (Thus, students will not see these statistics.) These will be shown in the moderator room in the form of percentages, i.e. the number of students that voted for "Too fast" or "Too slow" divided by the total amount of students in the room, multiplied by 100.
The default statistics displayed, upon entering the room, are: "0% think it is too slow" and "0% think it is too fast" .
Whenever a student presses "Reset" again, this vote will be revoked, and thus the percentages will be recalculated again and shown accordingly to the lecturer.

###### Zen Mode (Moderator room)
Moderators have the option to make their screen less cluttered by selecting the "Zen Mode" checkbox. This will cause functionalities, i.e. buttons and text fields, for editing, deleting and answering questions to disappear; both in the main list of questions as in the separate list of answered questions.

###### IP-banning of students
Moderators can ban certain students by navigating to the "Participants" tab and pressing the "Ban" button. (moderators cannot be banned) This will raise an alert displaying: "Are you sure you want to ban  <student nickname> ?"  Upon selecting "OK", this student user will get an alert displaying: "It seems like you got banned. The window will close for you now." The window will indeed close for this user and he/she will not be able to enter again by entering the (valid) room code. However, this user can still enter other rooms though. There is no way for moderators to undo this ban.

###### Ending lectures
Whenever a moderator presses "End lecture", the room will become inactive. This means students are not able to ask questions anymore. Submitting questions will display an alert: "The lecture has ended. You cannot ask questions anymore!" For all users, the window will remain open though. They are still allowed to see the questions (and answers) and current participants.
Moderators can still answer questions after the lecture has ended, and students will be able to see these answers when entering the room again.


###### Leaving the room and revisiting after lecture
Whenever a user leaves the room, he/she will be removed from the participants list.
Both students and moderators can revisit any room again (using a valid link), even after the lecture has been marked as ended.
Both open and closed questions (and answers) are still visible, with names of owners attached to these. Furthermore, moderators have the option to export all answered questions by pressing "Export questions" on the right top. This will save a copy, in the form of .txt or .md file, of all answered questions (and answer), together with a timestamp and nickname of the owner.


###### Multiple lectures running in sync
Whenever different rooms are active simultanuously, these will all have their own set of participants, (answered) questions and answers.
This can be tested by opening multiple instances of the application and entering distinct rooms. Asking a question in one room, will only lead to that question being displayed in that room in which it was being asked, not in other rooms.

