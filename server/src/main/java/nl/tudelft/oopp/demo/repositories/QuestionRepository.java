package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    List<Question> findAllByOrderByUpvotesDesc();

    List<Question> findQuestionsByRoomRoomIdAndIsAnsweredOrderByTimeDesc(long room, Boolean bool);

    @Transactional
    void deleteById(Long questionId);
}
