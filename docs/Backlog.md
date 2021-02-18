## Backlog group 43

* This backlog is our "to-do-list" containing all user stories/requirements based on the CTA interview in week 2.
* All user stories are assigned a MOWCOW label: either "must", "should", "could", or "won't".
* All user stories are converted to GitLab Issues.

| MUST feature | Stakeholder | Value | 
| --- | --- | --- |
| Users can join a certain (temporary) room for a lecture via a room link/code.  | All | Only users that have received a link can join a lecture session and staff/student roles are separated.  |
| See all other messages sent, with most relevant on top, i.e., having most upvotes.  | All  | Keeps the lecture well-organized for both teacher and student. | 
| Mark questions as answered/closed, removing it from the view for everyone. | Teacher/TA  | Keeps content relevant.  | 
| Have the option to delete any message sent by a user. | Teacher/TA  | Keeps content relevant, organized and appropriate. | 
| Mark questions as irrelevant, removing it from the view for everyone. | Teacher/TA  | Keeps content relevant, organized and appropriate. | 
| Teachers can schedule a certain room (link) before a lecture. | Teacher | Rooms can be reserved beforehand, thus making it convenient for teachers to share room links. | 
| Upvote questions from others. | Student | Makes sure questions are prioritized, keeping content relevant. | 
| Give live feedback or ask questions to teacher. | Student | Lectures in the future can be improved based on received feedback. Students can get answers to things that are not clear. | 
| Delete own questions only. | Student | Students can refrain from questions that are not relevant to them anymore. | 
| Close own questions only. | Student | Students can mark their question as answered, keeping content relevant and reducing load on teacher/TAs. | 

| SHOULD feature | Stakeholder | Value | 
| --- | --- | --- |
| Rephrase questions sent by students. | Teacher/TA | Keeps content appropriate and understandable for everyone. | 
| Merge questions by students into 1 question. | Teacher/TA | Keeps the view clean for everyone. | 
| Teachers should be able to see whether they are going to fast or slow. | Teacher | Teacher can adapt his/her speed according to feedback given by students, improving, lecture quality. | 
| Multiple lectures can run at the same time in separated rooms. | All | Each lecture can have its own set of messages, users etc. when multiple lectures are given simultaneously, keeping different course content separated for everyone. | 
| The application should refresh questions. | All | New questions that are still on the server are loaded on the client and thus can be seen by all users. | 


| COULD feature | Stakeholder | Value | 
| --- | --- | --- |
| Questions could be prioritized more efficiently, based on something other than upvotes. (age, length e.g.) | All | Makes sure that questions are prioritized in a way that is even more efficient than only regarding upvotes, I.e. always valid. | 
| Questions could be refreshed by pressing a button, pushing down from server or refreshing periodically. | All  | New questions that are still on the server are loaded on the client and thus can be seen by all users. | 
| Incorporate LaTeX support. | Student  | Provides a convenient way for displaying formulas etc. | 
| Let TAs forward questions to teachers; only those will be displayed for teachers | Teacher/TA | To keep teachers’ view uncluttered. | 
| Teachers have the option to do a poll at any time. | Teacher | Teachers can test students’ knowledge in an interactive manner or get other feedback. | 
| Teachers can show poll results to all students. | Teacher | Students can see how they performed compared to others or simply see results to a general poll. | 
| Provide a large-text / colour-blind mode. | All | Makes sure users with optical disabilities can access this platform as well. | 
| Students have the option to give a username before entering. | Student | Gives students the possibility to identify themselves / distinguish themselves from others. | 


| WON'T feature |
| --- | 
| No authentication of users, but based on room link (session token as identifier e.g.) | 
| Students’ names will not be shown, instead they are kept anonymous. |
| Teachers/TAs are not able to send messages; questions are answered verbally |  
| Students can’t reply directly to each other. |  
| Chat is not stored after the lecture has ended. |
| Users are not able to send images or other files, merely text. | 
| Don’t have the option to export questions after lecture. |