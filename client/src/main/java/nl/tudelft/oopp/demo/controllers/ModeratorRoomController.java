package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.data.Room;

public class ModeratorRoomController {

    private String name;
    private Room room;

    /**
     * Used in SplashController to pass the username and the room object.
     * @param name the name entered by the user in splash
     * @param room the room corresponding to the code entered
     */
    public void setData(String name, Room room) {
        this.name = name;
        this.room = room;
    }
}
