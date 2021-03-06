package nl.tudelft.oopp.demo.controllers;

import static nl.tudelft.oopp.demo.config.LoggerConfig.getFirstNumber;
import static nl.tudelft.oopp.demo.config.LoggerConfig.logRequest;

import java.util.List;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("questions")
public class QuestionController {

    private final QuestionService questionService;

    /**
     * Autowired constructor for the class.
     * @param questionService questionService
     */
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * GET mapping.
     * @return all questions from the database
     */
    @GetMapping
    @ResponseBody
    public List<Question> getQuestions() {
        logRequest("to get all questions from the database");
        return questionService.getQuestions();
    }

    /**
     * GET mapping.
     * @param roomId the id of the required room
     * @return all questions for a specific room
     */
    @GetMapping("/{roomId}")
    @ResponseBody
    public List<Question> getQuestionsByRoom(@PathVariable long roomId) {
        return questionService.getQuestionsByRoom(roomId);
    }

    /**
     * PUT mapping, adds a new question to the database.
     * @param payload data for the new question
     * @return the id of the new question
     */
    @PostMapping
    @ResponseBody
    public Long addNewQuestion(@RequestBody String payload) {
        Long questionId = questionService.addNewQuestion(payload);
        logRequest("to add a new question with an id '" + questionId
                + "' to the room with an id '" + getFirstNumber(payload) + "'");
        return questionId;
    }

    /**
     * GET mapping.
     * @param roomId the id of the required room
     * @return all answered questions for a specific room
     */
    @GetMapping("/answered/{roomId}")
    @ResponseBody
    public List<Question> getAnsweredQuestions(@PathVariable long roomId) {
        return questionService.getAnsweredQuestions(roomId);
    }

    /**
     * PUT mapping, marks a question in the DB as answered.
     * @param questionId the id of the required question
     */
    @PutMapping("/markAnswered/{questionId}")
    public void markQuestionAsAnswered(@PathVariable long questionId) {
        logRequest("to mark the question with an id '" + questionId + "' as answered");
        questionService.markQuestionAsAnswered(questionId);
    }

    /**
     * PUT mapping, sets a question isBeingWritten in the DB to true.
     * @param questionId the id of the required question
     */
    @PutMapping("/markAsBeingAnswered/{questionId}")
    public void markQuestionAsIsBeingAnswered(@PathVariable long questionId) {
        questionService.markQuestionAsIsBeingAnswered(questionId);
    }

    /**
     * PUT mapping, sets a question isBeingWritten in the DB to false.
     * @param questionId the id of the required question
     */
    @PutMapping("/markAsNotBeingAnswered/{questionId}")
    public void markQuestionAsNotBeingAnswered(@PathVariable long questionId) {
        questionService.markQuestionAsNotBeingAnswered(questionId);
    }

    /**
     * DELETE mapping, deletes a question from the DB.
     * @param questionId the id of the required question
     */
    @DeleteMapping("{questionId}")
    public void deleteQuestion(@PathVariable long questionId) {
        logRequest("to delete the question with an id '" + questionId + "'");
        questionService.deleteQuestion(questionId);
    }

    /**
     * PUT mapping, updates the text of a question in the DB.
     * @param questionId the id of the required question
     * @param question the new text for the question
     */
    @PutMapping("{questionId}")
    public void updateQuestion(@PathVariable long questionId,
                               @RequestBody String question) {
        logRequest("to update the question with an id '" + questionId + "'");
        questionService.updateQuestion(questionId, question);
    }

    /**
     * PUT mapping, sets an answer to a question.
     * @param questionId the id of the required question
     * @param answer the answer for the question
     */
    @PutMapping("/setanswer/{questionId}")
    public void setAnswer(@PathVariable long questionId,
                          @RequestBody String answer) {
        logRequest("to update the answer to the question with an id '" + questionId + "'");
        questionService.setAnswer(questionId, answer);
    }

    /**
     * PUT mapping, upvotes a question.
     * @param questionId the id of the required question
     */
    @PutMapping("upvote/{questionId}")
    public void upvote(@PathVariable long questionId) {
        questionService.upvote(questionId);
    }

    /**
     * PUT mapping, deupvotes a question.
     * @param questionId the id of the required question
     */
    @PutMapping("deupvote/{questionId}")
    public void deUpvote(@PathVariable long questionId) {
        questionService.deUpvote(questionId);
    }
}
