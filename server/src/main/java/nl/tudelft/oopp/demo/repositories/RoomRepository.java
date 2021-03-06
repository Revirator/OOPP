package nl.tudelft.oopp.demo.repositories;

import java.net.URL;

import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findById(long id);

    Room findFirstByStudentsLink(URL link);  // Those queries can't search by using a String

    Room findFirstByModeratorLink(URL link);
}