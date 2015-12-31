package org.leopub.seat;

import java.io.File;
import java.util.List;

public class Room {
    private String name;
    private List<String> seats;

    public Room(String name, List<String> seats) {
        this.name = name;
        this.seats = seats;
    }
    public Room(String name) throws SeatFileException {
        this.name = name;
        seats = Util.readFile(new File("./room/", name + ".txt"));
    }
    public String getName() {
        return name;
    }
    public List<String> getSeats() {
        return seats;
    }
}
