package nl.tudelft.oopp.demo.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /** Constructor for QuestionService.
     * @param questionRepository - retrieves questions from database.
     */
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    /** Called by QuestionController.
     * @return a List of questions ordered by number of upvotes.
     *          Example:
     *          GET http://localhost:8080/questions
     */
    public List<Question> getQuestions() {
        return questionRepository.findAllByOrderByUpvotesDesc();
    }

    public List<Question> getQuestionsByRoom(Room room) {
        return getQuestions()
                .stream()
                .filter(q -> q.getId().equals(room.getRoomId()))
                .collect(Collectors.toList());
    }


    /** Called by QuestionController.
     * @param question - new Question object to be stored in database.
     *                 Example:
     *     POST http://localhost:8080/questions
     *     Content-Type: application/json
        {
            "id": 7,
            "room": {"roomId":5,"studentsLink":"http://localhost:8080/rooms/5bb8da98f59d41ecbb","moderatorLink":"http://localhost:8080/rooms/M99cfa0c551fa4252b","startingTime":"2021-03-07 22:50:28","roomName":"Example Room","active":false,"participants":[],"questions":[]},
            "text": "What is the time complexity of merge sort??",
            "owner": "Albert",
            "time": "2021-02-09 22:13",
            "upvotes": 32
    }
     */
    public void addNewQuestion(Question question) {
        questionRepository.save(question);
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
        questionRepository.deleteById(questionId);
    }

    /** Called by QuestionController.
     * Changes content of question with this id into a new question.
     * @param questionId - Id of Question to be modified
     * @param question - new question as String
     *                 Example:
     *                 PUT http://localhost:8080/questions/6?question=Does this work?
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

    }

}