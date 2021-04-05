package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import nl.tudelft.oopp.demo.DemoApplication;
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


    /**
     * Uses a stream to filter out only the questions connected to the right room.
     * @param roomID current roomID
     * @return filtered list of questions
     */
    public List<Question> getQuestionsByRoom(long roomID) {
        return getQuestions()
                .stream()
                .filter(q -> q.getRoom() == roomID)
                .collect(Collectors.toList());
    }


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
     * @param payload - data sent by client containing roomId, owner, question.
     */
    public Long addNewQuestion(String payload) {

        String[] dataArray = payload.split("& ");
        long roomId = Long.valueOf(dataArray[0]);
        String questionOwner = dataArray[1];
        String questionText = dataArray[2];

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
     * Changes content of question with this id into a new question.
     * @param questionId - Id of Question to be modified
     * @param answer - answer to question as String (in requestBody)
     *                 Example:
     *                 PUT http://localhost:8080/questions/setanswer/6
     */
    @Transactional
    public void setAnswer(Long questionId, String answer) {
        Question questionToModify = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new IllegalStateException("Question with id "
                                + questionId + " does not exist!"));

        if (answer != null && answer.length() > 0
                && !Objects.equals(questionToModify.getAnswer(), answer)) {
            questionToModify.setAnswer(answer);
        }
        System.out.println("######## SET ANSWER TO QUESTION ID: " + questionId
                + " ################");
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

        System.out.println("######## MARKED ANSWERED QUESTION ID: "
                + questionId + " ################");
    }



    /** Called by QuestionController.
     * Changes the isBeingWritten value of a question to true.
     * @param questionId - Id of Question to be modified
     *                 Example:
     *                 PUT http://localhost:8080/questions/markAsIsBeingWritten/2
     */
    @Transactional
    public void markQuestionAsIsBeingAnswered(long questionId) {
        Question questionToModify = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new IllegalStateException("Question with id "
                                + questionId + " does not exist!"));

        questionToModify.setIsBeingAnswered();

        System.out.println("######## MARKED TRUE ISBEINGANSWERED QUESTION ID: "
                + questionId + " ################");
    }



    /** Called by QuestionController.
     * Changes the isBeingWritten value of a question to false.
     * @param questionId - Id of Question to be modified
     *                 Example:
     *                 PUT http://localhost:8080/questions/markAsIsBeingWritten/2
     */
    @Transactional
    public void markQuestionAsNotBeingAnswered(long questionId) {
        Question questionToModify = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new IllegalStateException("Question with id "
                                + questionId + " does not exist!"));

        questionToModify.setIsNotBeingAnswered();

        System.out.println("######## MARKED FALSE ISBEINGANSWERED QUESTION ID: "
                + questionId + " ################");
    }



    /** Called by QuestionController.
     * Increments the upvote amount by one of the question with provided id.
     * @param questionId - Id of Question to be incremented
     *                 Example:
     *                 PUT http://localhost:8080/questions/upvote/42
     */
    @Transactional
    public void upvote(Long questionId) {
        Question questionToModify = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new IllegalStateException("Question with id "
                                + questionId + " does not exist!"));
        questionToModify.upvote();
        System.out.println("######## UPVOTED QUESTION ID: " + questionId + " ################");
    }



    /** Called by QuestionController.
     * Decrements the upvote amount by one of the question with provided id.
     * @param questionId - Id of Question to be decremented
     *                 Example:
     *                 PUT http://localhost:8080/questions/deupvote/42
     */
    @Transactional
    public void deUpvote(Long questionId) {
        Question questionToModify = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new IllegalStateException("Question with id "
                                + questionId + " does not exist!"));
        questionToModify.deUpvote();
        System.out.println("######## DE-UPVOTED QUESTION ID: " + questionId + " ################");
    }
}