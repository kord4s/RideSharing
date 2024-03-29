package com.example.ridesharing.PickUpPoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pickuppoint")
//@CrossOrigin(origins = "") <--- adres frontu
public class PickUpPointController {
    private final PickUpPointService pickUpPointService;

    @Autowired
    public PickUpPointController(PickUpPointService pickUpPointService) {
        this.pickUpPointService = pickUpPointService;
    }

    @PostMapping("/add")
    public ResponseEntity<PickUpPoint> addNewPickUpPoint(Double XMap, Double YMap){
        return new ResponseEntity<>(pickUpPointService.addNewPickUpPoint(XMap,YMap), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PickUpPoint> getPickUpPointByID(@PathVariable("id") Long id){
        return new ResponseEntity<>(pickUpPointService.getPickUpPoint(id), HttpStatus.OK);
    }

    /// sortowanie po odleglosci od uzytkownika???
    @GetMapping("/get/all")
    public ResponseEntity<List<PickUpPoint>> getAllPickUpPoints(){
        return new ResponseEntity<>(pickUpPointService.getAllPickUpPoints(), HttpStatus.OK);
    }

    @GetMapping("/get/endpoints")
    public ResponseEntity<List<PickUpPoint>> getAllEndPoints(){
        return new ResponseEntity<>(pickUpPointService.getAllEndingPickUpPoints(), HttpStatus.OK);
    }
}
