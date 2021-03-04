package nl.tudelft.oopp.demo.services;

import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.repositories.QuestionRepository;
import nl.tudelft.oopp.demo.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    /** Constructor for RoomService.
     * @param roomRepository - retrieves rooms from database.
     */
    @Autowired
    public RoomService(RoomRepository roomRepository) {this.roomRepository = roomRepository; }

    /** Called by RoomController.
     * @return a List of rooms.
     */
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    /** Called by RoomController.
     * @param id the id of room.
     * @return the room itself.
     */
    public Room getRoomById(long id) {
        return roomRepository.findById(id);
    }

    /** Called by RoomController.
     * @param code the code (link?) of room.
     * @return the room itself.
     */
    public Room getRoomByCode(String code) {
//        char last = code.charAt(code.length() - 1);
//        if(last == 'S') {       // Check if the code is for a student or a moderator (probably will get changed later)
//            return roomRepository.findByStudentsLink(code);
//        } else {
//            return roomRepository.findByModeratorLink(code);
//        }
        URI uri = null;
        try {
            uri = new URI(code);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return roomRepository.findFirstByStudentsLink(url);

    }

}
