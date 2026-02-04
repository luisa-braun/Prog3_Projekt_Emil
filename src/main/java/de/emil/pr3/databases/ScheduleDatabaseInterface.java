package de.emil.pr3.databases;

import de.emil.pr3.shift.ShiftAssignment;

import java.util.Date;
import java.util.List;

public interface ScheduleDatabaseInterface {

    List<ShiftAssignment> saveSchedule(List<ShiftAssignment> schedule, Date weekStartDate) throws IllegalArgumentException;

    List<ShiftAssignment> getScheduleByWeek(Date weekStartDate) throws IllegalArgumentException;

    void validateId(int id) throws IllegalArgumentException;
}
