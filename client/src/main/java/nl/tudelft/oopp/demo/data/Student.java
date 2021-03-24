package nl.tudelft.oopp.demo.data;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Student extends User {

    private String IpAddress;
    private boolean banned;

    public Student(Long id, String nickname, Room room, String IpAddress, boolean banned) {
        super(id, nickname, room);
        this.IpAddress = IpAddress;
        this.banned = banned;
    }

    public Student(String username, Room room) {
        super(username, room);
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

    @Override
    public String getRole() {
        return super.getRole();
    }

    @Override
    public String toString() {
        return "Student " + super.getNickname() + " in lecture " + super.getRoom().getRoomName();
    }
}

