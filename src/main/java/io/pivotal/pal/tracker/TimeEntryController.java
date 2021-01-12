package io.pivotal.pal.tracker;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry resultEntry = timeEntryRepository.create(timeEntryToCreate);
        return ResponseEntity.created(null).body(resultEntry);
    }

    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry resultEntry = timeEntryRepository.find(timeEntryId);

        if (resultEntry != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultEntry);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> resultEntryList = timeEntryRepository.list();
        return ResponseEntity.status(HttpStatus.OK).body(resultEntryList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable(value = "id") long timeEntryId,
                                            @RequestBody TimeEntry timeEntryToUpdate) {
        TimeEntry updatedEntry = timeEntryRepository.update(timeEntryId, timeEntryToUpdate);

        if (updatedEntry != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedEntry);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
