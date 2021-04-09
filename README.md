## QuestionMe: new online lecture platform

In this project for the Computer Science and Engineering course Object Oriented Programming Project (OOPP – CSE1105), we are working in a team of six students to create a brand-new desktop application, which may potentially be used in actual lectures.
This new technology is meant to support lectures during online education, by helping them maintain a clutter-free overview of the most relevant questions asked by students. Furthermore, students can provide live feedback, giving lecturers an indication of their speed.

This application relies on the following tools:
- Front-end platform: `JavaFX`
- Back-end framework: `Spring`
  - Communication between server and client: `REST JSON API` 
- Database: `H2` for development, `PostgreSQL` for production
    - Communication between server and database: `Hibernate`, `Spring JPA`
- Build automation tool: `Gradle`
- Testing framework: `JUnit 5` 
- Code style checker: `CheckStyle`


## Main features

- Users can schedule a certain room (link) before a lecture
- Users can join a certain session room for a lecture via a room code
- Users can instantly create rooms to start meetings
- Users are kept in a waiting room until lecture starts


- Students can ask questions during the lecture time slot only
- Students can edit and delete their own questions
- Moderators can edit, delete and answer any question
- Students can give live feedback to lecturers
- Students can upvote any question
    - Moderators will see questions with most upvotes on top


- Students can mark their own questions as answered
- Moderators can mark any question as answered
  - All answered questions move to a separate list 
    
    
- Moderators can end lectures 
- After a lecture has ended, moderators can export all answered questions
- Users can revisit ended lectures and see all questions (with answers)
- Moderators can enable a Zen Mode, which will make the view uncluttered by hiding certain buttons


## Group members (group 43)

| 📸 | Name | Email |
|---|---|---|
| ![](https://eu.ui-avatars.com/api/?name=NK&length=4&size=50&color=DDD&background=777&font-size=0.325) |   Nadine Kuo   | H.N.Kuo@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=SB&length=4&size=50&color=DDD&background=777&font-size=0.325) |   Senne Van den Broeck   | S.M.Z.VandenBroeck@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=PG&length=4&size=50&color=DDD&background=777&font-size=0.325) |   Pavel Germanov   | P.Germanov@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=BG&length=4&size=50&color=DDD&background=777&font-size=0.325) |   Bora Göral   | B.Goral@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=EG&length=4&size=50&color=DDD&background=777&font-size=0.325) |   Emke de Groot   | E.A.deGroot-1@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=DT&length=4&size=50&color=DDD&background=777&font-size=0.325) |   Denis Tsvetkov   | D.R.Tsvetkov@student.tudelft.nl |



## How to run it


### Download

You can either download the code directly as ZIP file [here](https://gitlab.ewi.tudelft.nl/cse1105/2020-2021/team-repositories/oopp-group-43/repository-template/-/archive/master/repository-template-master.zip) or:

- Clone our repository using SSH via [this link](git@gitlab.ewi.tudelft.nl:cse1105/2020-2021/team-repositories/oopp-group-43/repository-template.git)
- Clone our repository using HTTPS via [this link](https://gitlab.ewi.tudelft.nl/cse1105/2020-2021/team-repositories/oopp-group-43/repository-template.git)


### Configure database

Before starting the server, you might want to configure your database settings.
We worked with Spring Profiles to switch between H2 for development purposes, and PostgreSQL for production purposes. 
This can be done by navigating to `application.properties` and set `spring.profiles.active= ` to either `development` for H2 or `production` for PostgreSQL.
These will invoke the H2Config and PostgresConfig files respectively.

For setting up PostgreSQL, copy the contents of the `application-production.template.properties` file into a new file named `application-production.properties`. 
Here, a username and password can be added. Also, `spring.jpa.hibernate.ddl-auto=` can be set to either `create-drop` or `validate`,
causing data to be dropped or preserved respectively after server restart.
This file is ignored by git, thus any changes you make to it will not be pushed to remote.
The same can be done for H2 by using the `application-dev.template.properties` file instead.


When using PostgreSQL, all tables will be inserted into a database named 'Question', 
thus this database will have to be created beforehand.


In the folder `nl.tudelft.oopp.questionme.config`, our Config files can be found, serving as database loaders.

### Starting the server

Start up the server by navigating to the `DemoApplication` file under `nl.tudelft.oopp.questionme` and simply run this class.
To open the client application, navigate to `MainApp` under `nl.tudelft.oopp.questionme` and similarly, run this class.
This should open a window displaying our splash screen. 

In your console, you will see server logs containing information about each server request, 
linked to a corresponding IP-address and timestamp. These logs are all gathered into a `spring.log`
file, which is ignored by git.


## How to contribute to it

### Set up project environment

- Make sure to have an updated version of IntelliJ installed on your machine. 
  (preferably IntelliJ Ultimate Edition, which can be installed via [this link](https://www.jetbrains.com/idea/))


- Clone this repository on your local machine, using SSH via [this link](git@gitlab.ewi.tudelft.nl:cse1105/2020-2021/team-repositories/oopp-group-43/repository-template.git)
  or using HTTPS via [this link](https://gitlab.ewi.tudelft.nl/cse1105/2020-2021/team-repositories/oopp-group-43/repository-template.git).


- In `application.properties`, set `spring.profiles.active= ` to `development` to use the in-memory database H2.
  Copy the contents of the `application-dev.template.properties` file into a new file named `application-dev.properties`.
  Here, a username and password can be added for instance. The latter file is ignored by git. 

### Pushing commits to GitLab

- Make use of isolated feature branches, which will be merged into `development_branch` 
via Merge Requests in GitLab. 
    - To ensure code quality, set the minimum number of approvals needed 
      before merging to 2 or even higher.
    - When we are sure `development_branch` is stable, this branch will be merged into `MASTER`.
  

- Make use of proper, descriptive commit messages and MR descriptions.


- Test your code thoroughly before committing the work.
We try to keep the total test coverage 70% or higher.
  

- Make sure the GitLab CI passes. 
This can be checked locally by running `./gradlew clean` followed by `./gradle build`.
  - This pipeline checks building, CheckStyle and runs all tests for both client and server side.