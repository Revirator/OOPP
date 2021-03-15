package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import javax.transaction.Transactional;
import nl.tudelft.oopp.demo.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    List<Question> findAllByOrderByUpvotesDesc();

    @Transactional
    void deleteById(Long questionId);

    //    List<Question> findAllByRoom(Room room);

}
