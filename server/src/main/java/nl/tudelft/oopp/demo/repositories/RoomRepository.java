package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.URL;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findById(long id);
    Room findByStudentsLink(String link);
    Room findByModeratorLink(String link);
}
