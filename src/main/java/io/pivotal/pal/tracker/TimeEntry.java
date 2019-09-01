package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {

    private Long id;
    private Long projectId;
    private Long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry() {

    }

    public TimeEntry(Long id, Long projectId, Long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(Long projectId, Long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(Integer projectId, Integer userId, LocalDate date, int hours) {
        this.projectId = projectId.longValue();
        this.userId = userId.longValue();
        this.date = date;
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object object) {
        TimeEntry timeEntry = (TimeEntry)object;
        return this.date.isEqual( timeEntry.date)
            && this.projectId.equals(timeEntry.projectId) && this.userId.equals(timeEntry.userId)
            && this.hours == timeEntry.hours;
    }
}
