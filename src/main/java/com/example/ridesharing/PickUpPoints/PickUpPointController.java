package com.example.ridesharing.PickUpPoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
//@CrossOrigin(origins = "") <--- adres frontu
public class PickUpPointController {
    private final PickUpPointService pickUpPointService;

    @Autowired
    public PickUpPointController(PickUpPointService pickUpPointService) {
        this.pickUpPointService = pickUpPointService;
    }
}
