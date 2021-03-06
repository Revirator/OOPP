package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.data.Room;

public class StudentRoomController {

    private String name;
    private Room room;

    public void setData(String name, Room room) {
        this.name = name;
        this.room = room;
    }
}
