package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Question;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public List<Question> getQuestions() {
        return questionRepository.findAllByOrderByUpvotesDesc();
    }


    public void addNewQuestion(Question question) {
        questionRepository.save(question);
    }

    public void deleteQuestion(Long questionId) {
        questionRepository.findById(questionId);
        boolean exists = questionRepository.existsById(questionId);
        if (!exists) {
            throw new IllegalStateException("Question with id " + questionId + " does not exist!");
        }
        questionRepository.deleteById(questionId);
    }

    @Transactional
    public void updateQuestion(Long questionId, String question) {
        Question questionToDelete = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalStateException("Question with id " + questionId + " does not exist!"));

        if (question != null && question.length() > 0 && !Objects.equals(questionToDelete.getQuestion(), question)) {
            questionToDelete.setQuestion(question);
        }

    }

}