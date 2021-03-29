package nl.tudelft.oopp.demo.entities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends User {

    private String ipAddress;
    private boolean banned;

    public Student() {

    }

    /** Initializes a new instance of student that is added to the DB.
     * @param nickname the nickname of the student
     * @param room the room the student is in
     * @param ipAddress the IP address of the student
     */
    public Student(String nickname, Room room, String ipAddress) {
        super(nickname, room);
        this.ipAddress = ipAddress;
        this.banned = false;
    }

    /** Initializes a new instance of student that is added to the DB.
     * @param id the id of the student in the DB
     * @param nickname the nickname of the student
     * @param room the room the student is in
     */
    public Student(long id, String nickname, Room room) {
        super(id, nickname, room);
        try {
            this.ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.banned = false;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public boolean isBanned() {
        return this.banned;
    }

    public void ban() {
        this.banned = true;
    }

    @Override
    public String toString() {
        return "Student " + super.getNickname() + " in room " + getRoom();
    }
}

