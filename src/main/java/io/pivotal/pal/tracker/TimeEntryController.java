package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        return new ResponseEntity(timeEntryRepository.create( timeEntry), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry timeEntry = timeEntryRepository.find( id);
        if( timeEntry == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity( timeEntry, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry timeEntryUpdated = timeEntryRepository.update( id, timeEntry);
        if( timeEntryUpdated == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return new ResponseEntity( timeEntryUpdated, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        TimeEntry timeEntryUpdated = timeEntryRepository.delete( id);
        return new ResponseEntity( timeEntryUpdated, HttpStatus.NO_CONTENT);
    }
}
