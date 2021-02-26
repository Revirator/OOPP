package nl.tudelft.oopp.demo.repositories;

// QuoteConfig (commandlinerunner)
// Load stuff in database
// Only to be used during development!! Not production

import nl.tudelft.oopp.demo.entities.Quote;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoader {

    public DatabaseLoader(QuoteRepository repository) {

        Quote q1 = new Quote(
                1,
                "A clever person solves a problem. A wise person avoids it.",
                "Albert Einstein"
        );

        Quote q2 = new Quote(
                2,
                "The computer was born to solve problems that did not exist before.",
                "Bill Gates"
        );

        Quote q3 = new Quote(
                3,
                "Tell me and I forget.  Teach me and I remember.  Involve me and I learn.",
                "Benjamin Franklin"
        );

        repository.save(q1);
        repository.save(q2);
        repository.save(q3);

    }

}
