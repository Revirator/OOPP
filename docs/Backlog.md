## Backlog group 43

* This backlog is our "to-do-list" containing all user stories/requirements based on the CTA interview in week 2.
* All user stories are assigned a MOSCOW label: either "must", "should", "could", or "won't".
* All user stories are converted to GitLab Issues.

| MUST feature | Stakeholder | Value | 
| --- | --- | --- |
| Users can schedule a certain room (link) before a lecture. | All | Rooms can be reserved beforehand, thus making it convenient for teachers to share room links. | 
| Users can join a certain session room for a lecture via a room link/code. (splashscreen) | All | Only users that have received a link can join a lecture session and staff/student roles are separated.  |
| Users can enter a username before entering the lecture (splashscreen). | All | Users can identify themselves.| 
| Users can instantly create rooms to start meetings. (splashscreen) | All | Users can have meeting with a group of participants whenever they want to. (lecturer can start any lecture) | 
| Users are kept in a waiting room until lecture starts. | All | This prevents users from joining the lecture before it has started. | 
| Lecture/meeting starts automatically after user presses “Create new room”. | All | Users don’t have to start a meeting manually. | 
| Lecture/meeting starts automatically when assigned lecture time slot starts. | All | Users don’t have to start a meeting manually. | 
| The application should refresh messages constantly and display them to users. | All | New questions that are still on the server are loaded onto the client and thus can be seen by all users. |
| See all (updated) questions sent by any user with nicknames of their owner. | All | Users can see whom a question/feedback message belongs to. | 
| See most relevant questions on top, i.e., having most upvotes.  | All  | Keeps the lecture well-organized for both teacher and student. | 
| Have a separate view for all answered questions (with answers, if answered in text) | All | Users can look back at questions that have been answered (with their answers.) |
| Give live feedback to teacher. | Student | Lectures in the future can be improved based on received feedback. Students can get answers to things that are not clear. | 
| Can ask questions during the lecture time slot only. | Student | Students can get answers to lecture material that is not clear to them. | 
| Upvote questions from others. (or their own) | Student | Makes sure questions are prioritized, keeping content relevant. |
| Edit own questions only. | Student | Students can rephrase their questions to make them clearer e.g. |
| Delete own questions only. | Student | Students can refrain from questions that are not relevant to them anymore. | 
| Mark own questions only as answered. | Student | Students can mark their question as answered, keeping content relevant and thereby helping teacher/TAs. | 
| Mark any question as answered | Teacher/TA  | Keeps content relevant.  | 
| All questions marked as answered are removed from the view for everyone. | All | Display stays clean and relevant for all users. |
| All questions marked as answered end up in a separate list. (with answer, if answered in text) | All | All users can have a clean overview of questions that have been answered (with their answers.) |
| Have the option to delete any message sent by a user. | Teacher/TA  | Keeps content relevant, organized and appropriate. | 
| Teachers can end lectures/stop meeting. (button) | Teacher/TA | This prevents students from asking more questions. |



| SHOULD feature | Stakeholder | Value | 
| --- | --- | --- |
| Multiple lectures can run at the same time in separated rooms. | All | Each lecture can have its own set of messages, users etc. when multiple lectures are given simultaneously, keeping different course content separated for everyone. | 
| Having the option to see all current participants (nicknames) in the lecture. | All | All users can have an overview of who  they are in the meeting with currently. |
| Limit rate of questions. (certain number of questions per time unit) | System admin | System admin can configure the number of questions per time unit and thereby reduce server load. | 
| Teachers should be able to see whether they are going to fast or slow. | Teacher | Teacher can adapt his/her speed according to feedback given by students, improving, lecture quality. |
| Rephrase questions sent by students. | Teacher/TA | Keeps content appropriate and understandable for everyone. | 
| Moderators can answer questions in text. | (Teacher)/TA | Questions can be answered without the lecturer (by the TAs) | 
| Moderators can answer questions in text, that have been answered verbally by the lecturer. | (Teacher)/TA | Verbal answered are stored in the system as well. | 
| After lecture has ended, lecturers/TAs can still answer open questions in text. | Teacher/TA | Students can still get answers to their questions if not answered during the lecture. | 
| Users can revisit a lecture session by entering the same room code. | All | Allows access to an old room. | 
| After re-entering a room, users can see all questions and answers. (answered during or after lecture) | All | Users can reread questions/answers at a later point. | 
| After re-entering a room, moderators can export all questions and answers. | Teacher/TA | Helps the staff to store old questions on their machines. |
| Having event logs keep track of and display system events. | System admin | Helps admins maintain the system and recover from system crashes (by accessing server logs, they can see where errors occurred). | 
| Admins can recover after a system crash, without losing data. | System admin | All messages sent will not be lost. | 
| After a system crash, admins can let all participants join again. | System admin | Helps to restore the lecture faster in case of crash. | 
| After a system crash, admins can put all sent questions back, thereby linking them to their owner. | System admin | After recovering, users will be able to see all messages that had been sent before again. | 



| COULD feature | Stakeholder | Value | 
| --- | --- | --- |
| Questions could be prioritized more efficiently, based on something other than upvotes. (age, length e.g.) | All | Makes sure that questions are prioritized in a way that is even more efficient than only regarding upvotes, I.e. always valid. | 
| Questions could be refreshed by pressing a button, pushing down from server or refreshing periodically. | All  | New questions that are still on the server are loaded on the client and thus can be seen by all users. | 
| Teachers have the option to open a MC poll at any time. | Teacher/(TA) | Teachers can test students’ knowledge in an interactive manner or get other feedback. |
| Have the option to open a MC poll at any time. | Teacher/(TA) | Teachers can stop students from sending answers whenever he/she wants to.| 
| Students can answer polls. | Student | Students get to share their opinion/answer. | 
| Teachers can show poll results to all students. | Teacher | Students can see how they performed compared to others or simply see results to a general poll. |

| WON'T feature |
| --- | 
| No authentication of users, but based on room link (session token as identifier e.g.) | 
| TAs and lecturers don’t have different permissions. | 
| Students can’t reply directly to each other. |
| Users are not able to send images or other files, merely text. |
| No LaTeX support | 
| No large text/colour-blind mode. |
| No merging of question (by moderators.) | 
| No downvoting (unless we have good reasons for it) | 
| No need for a TA to forward the question to a lecturer (same view for both of them) |  
| Teachers are not able to do polls with open questions. | 