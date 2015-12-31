package org.leopub.seat;

import java.io.File;
import java.util.List;

public class Unit {
    private String name;
    private List<String> members;

    public Unit(String name, List<String> members) {
        this.name = name;
        this.members = members;
    }

    public Unit(String name) throws SeatFileException {
        this.name = name;
        members = Util.readFile(new File("./class/", name + ".txt"));
    }
    public String getName() {
        return name;
    }

    public List<String> getMembers() {
        return members;
    }
}
