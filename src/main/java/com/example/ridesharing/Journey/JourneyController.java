package com.example.ridesharing.Journey;

import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journey")
//@CrossOrigin(origins = "") <--- adres frontu
public class JourneyController {
    private final JourneyService journeyService;

    @Autowired
    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @PostMapping("/add")
    public ResponseEntity<Journey> addJourney(@RequestBody Journey journey, @RequestParam Long carID){
        return new ResponseEntity<>(journeyService.nextStepAddingJourney(journeyService.addJourney(journey), carID), HttpStatus.OK);
    }

    @PutMapping("/{id}/addPickUpPoint")
    public ResponseEntity<Journey> addPickUpPoint(@PathVariable("id") Long id, @RequestBody JourneyPickUp journeyPickUp, @RequestParam Long startID, @RequestParam Long endID){
        return new ResponseEntity<>(journeyService.finishAddingJourney(id, journeyPickUp, startID, endID),HttpStatus.OK);
    }
}
