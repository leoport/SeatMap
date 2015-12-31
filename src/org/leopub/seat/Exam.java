package org.leopub.seat;

import java.util.Date;
import java.util.List;

public class Exam {
    private String courseName;
    private Room room;
    private List<Unit> units;
    private Date startTime;
    private Date endTime;
    private List<String> VIPs;

    public Exam(String courseName, Room room, Date startTime, Date endTime, List<Unit> units, List<String> VIPs) {
        this.courseName = courseName;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.units = units;
        this.VIPs = VIPs;
    }
    public String getCourseName() {
        return courseName;
    }
    public Room getRoom() {
        return room;
    }
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public List<Unit> getUnits() {
        return units;
    }
    public List<String> getVIPs() {
        return VIPs;
    }
}
