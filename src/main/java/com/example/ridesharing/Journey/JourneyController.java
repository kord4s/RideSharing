package com.example.ridesharing.Journey;

import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get/{id}")
    public ResponseEntity<Journey> getJourney(@PathVariable("id") Long id){
        return new ResponseEntity<>(journeyService.getJourneyByID(id), HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Journey>> getAllJourneys(){
        return new ResponseEntity<>(journeyService.getAllJourneys(), HttpStatus.OK);
    }


    @PutMapping("/{id}/addFirstPickUpPoint")
    public ResponseEntity<Journey> addPickUpPoint(@PathVariable("id") Long id, @RequestBody JourneyPickUp journeyPickUp, @RequestParam Long startID, @RequestParam Long endID){
        return new ResponseEntity<>(journeyService.finishAddingJourney(id, journeyPickUp, startID, endID),HttpStatus.OK);
    }

    @PutMapping("/{id}/addNextPickUpPoint/exist")
    public ResponseEntity<Journey> addNextExistingPickUpPoint(@PathVariable("id") Long id, @RequestBody JourneyPickUp journeyPickUp, @RequestParam Long startID, @RequestParam Long userID){
        return new ResponseEntity<>(journeyService.addNextJourneyPickUpPointWithExistingPickUpPoint(journeyPickUp,id,userID, startID), HttpStatus.OK);
    }

    @PutMapping("/{id}/addNextPickUpPoint/new")
    public ResponseEntity<Journey> addNextNewPickUpPoint(@PathVariable("id") Long id, @RequestBody JourneyPickUp journeyPickUp, @RequestParam Double XMap, @RequestParam Double YMap, @RequestParam Long userID){
        return new ResponseEntity<>(journeyService.addNextJourneyPickUpPointWithNewPickUpPoint(journeyPickUp,id, userID, XMap, YMap), HttpStatus.OK);
    }

    @PutMapping("/{id}/orderPickUpPoints")
    public ResponseEntity<Journey> orderJourneyPickUpPoints(@PathVariable("id") Long id){
        return new ResponseEntity<>(journeyService.findOrderOfPickUpPoints(id), HttpStatus.OK);
    }

    @GetMapping("/getPossibleJourneys/exist")
    public ResponseEntity<List<Journey>> findAllPossibleJourneys(@RequestParam Long startID, @RequestParam Long endID){
        return new ResponseEntity<>(journeyService.findPossibleJourneysWithExistingPickUpPoint(startID,endID), HttpStatus.OK);
    }

    @PutMapping("/{id}/finishJourney")
    public ResponseEntity<Journey> finishJourney(@PathVariable Long id){
        return new ResponseEntity<>(journeyService.finishJourney(id),HttpStatus.OK);
    }

    @PutMapping("/{id}/unfinishedJourney")
    public ResponseEntity<Journey> unfinishedJourney(@PathVariable Long id){
        return new ResponseEntity<>(journeyService.notFinishedJourney(id),HttpStatus.OK);
    }

    @GetMapping("/getPossibleJourneys/new")
    public ResponseEntity<List<Journey>> findAllPossibleJourneys(@RequestParam Double XMap, @RequestParam Double YMap, @RequestParam Long endID){
        return new ResponseEntity<>(journeyService.findPossibleJourneysWithNewPickUpPoint(XMap, YMap, endID), HttpStatus.OK);
    }

}
