package nl.tudelft.oopp.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Entity
@Table(name = "students")
public class Student extends User {

    private String IpAddress;
    private boolean banned;

    public Student() {

    }

    public Student(String nickname, Room room) {
        super(nickname, room);
        try {
            this.IpAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.banned = false;
    }

    public Student(long id, String nickname, Room room) {
        super(id, nickname, room);
        try {
            this.IpAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.banned = false;
    }

    public String getIpAddress() {
        return this.IpAddress;
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

