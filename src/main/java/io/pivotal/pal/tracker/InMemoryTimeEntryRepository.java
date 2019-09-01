package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository  {

    Map<Long, TimeEntry> timeEntryMap = new HashMap<>();
    volatile long idCounter = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId( idCounter++);
        timeEntryMap.put( timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntryMap.get( id);
    }

    @Override
    public List<TimeEntry> list() {
        return Collections.unmodifiableList( new ArrayList( timeEntryMap.values()));
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if( timeEntryMap.containsKey( id)) {
            timeEntry.setId( id);
            timeEntryMap.replace(id, timeEntry);
            return timeEntry;
        }
        else
        {
            return null;
        }
    }

    @Override
    public TimeEntry delete(long id) {
        return timeEntryMap.remove( id);
    }
}
