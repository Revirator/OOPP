package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final RoomRepository roomRepository;


    /** Constructor for QuestionService.
     * @param questionRepository - retrieves questions from database.
     */
    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           RoomRepository roomRepository) {
        this.questionRepository = questionRepository;
        this.roomRepository = roomRepository;
    }

    /** Called by QuestionController.
     * @return a List of questions ordered by number of upvotes.
     *          Example:
     *          GET http://localhost:8080/questions
     */
    public List<Question> getQuestions() {
        return questionRepository.findAllByOrderByUpvotesDesc();
    }


    //    /** Called by QuestionController.
    //     * @param question - new Question object to be stored in database.
    //     *                 Example:
    //     *     POST http://localhost:8080/questions
    //     *     Content-Type: application/json
    //        {
    //            "id": 7,
    //            "room": {"roomId":5,"studentsLink":"http://localhost:8080/rooms/5bb8da98f59d41ecbb","moderatorLink":"http://localhost:8080/rooms/M99cfa0c551fa4252b","startingTime":"2021-03-07 22:50:28","roomName":"Example Room","active":false,"participants":[],"questions":[]},
    //            "text": "What is the time complexity of merge sort??",
    //            "owner": "Albert",
    //            "time": "2021-02-09 22:13",
    //            "upvotes": 32
    //    }
    //     */
    //    public void addNewQuestion(Question question) {
    //        questionRepository.save(question);
    //    }

    /** Called by QuestionController.
     * @param room - the id of the room of which we want the questions
     * @return a List of questions from a with an answer (text) ordered by time.
     *          Example:
     *          GET http://localhost:8080/questions/answered/1
     */
    public List<Question> getAnsweredQuestions(long room) {
        return questionRepository.findQuestionsByRoomRoomIdAndIsAnsweredOrderByTimeDesc(room, true);
    }

    /** Parses data sent by client to create a new Question with id.
     * Stores new question in database.
     * Called by QuestionController.
     * @param payload - data sent by client containing roomId, question, owner.
     */
    public Long addNewQuestion(String payload) {

        String[] dataArray = payload.split(", ");
        long roomId = Long.valueOf(dataArray[0]);
        String questionText = dataArray[1];
        String questionOwner = dataArray[2];

        boolean exists = roomRepository.existsById(roomId);
        if (!exists) {
            throw new IllegalStateException("Room with id " + roomId + " cannot be found!");
        }
        Room room = roomRepository.findById(roomId);

        Question newQuestion = new Question(room, questionText, questionOwner);
        questionRepository.save(newQuestion);
        System.out.println("############# NEW QUESTION ID: "
                + newQuestion.getId() + " #################");
        return newQuestion.getId();
    }



    /** Called by QuestionController.
     * @param questionId - Id of Question to be removed from database.
     *                   Example:
     *                   DELETE http://localhost:8080/questions/2
     */
    public void deleteQuestion(Long questionId) {
        questionRepository.findById(questionId);
        boolean exists = questionRepository.existsById(questionId);
        if (!exists) {
            throw new IllegalStateException("Question with id " + questionId + " does not exist!");
        }
        System.out.println("######## DELETED QUESTION ID: " + questionId + " ################");
        questionRepository.deleteById(questionId);
    }

    /** Called by QuestionController.
     * Changes content of question with this id into a new question.
     * @param questionId - Id of Question to be modified
     * @param question - new question as String (in requestBody)
     *                 Example:
     *                 PUT http://localhost:8080/questions/6
     */
    @Transactional
    public void updateQuestion(Long questionId, String question) {
        Question questionToModify = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new IllegalStateException("Question with id "
                                + questionId + " does not exist!"));

        if (question != null && question.length() > 0
                && !Objects.equals(questionToModify.getText(), question)) {
            questionToModify.setText(question);
        }
        System.out.println("######## EDITED QUESTION ID: " + questionId + " ################");
    }

    /** Called by QuestionController.
     * Changes the isAnswered value of a question with this id.
     * @param questionId - Id of Question to be modified
     *                 Example:
     *                 PUT http://localhost:8080/questions/markAnswered/2
     */
    @Transactional
    public void markQuestionAsAnswered(long questionId) {
        Question questionToModify = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new IllegalStateException("Question with id "
                                + questionId + " does not exist!"));

        questionToModify.setAsAnswered();

        System.out.println("######## EDITED QUESTION ID: " + questionId + " ################");
    }
}